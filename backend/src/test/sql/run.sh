#!/bin/bash
#set -x
bold=$(tput bold)
normal=$(tput sgr0)
verbose1="1>/dev/null"
verbose2="1>/dev/null 2>/dev/null"
status="0"
positive_enabled=1
negative_enabled=1

test_count=0
fail_count=0
while getopts 'vVhpn' opt; do
  case "$opt" in
    v)
        verbose2=""
      ;;

    V)
        verbose1=""
        verbose2=""
      ;;

    p)
        negative_enabled=0
      ;;

    n)
        positive_enabled=0
      ;;

    ?|h)
      echo "Usage: $(basename $0) [-vVhpn]"
      exit 1
      ;;
  esac
done

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

echo ""
dir=$(dirname "$0")

if [ -f "$dir/initial.sql" ]; then
  echo "${bold}Running initial script...${normal}"
  eval "PGPASSWORD=postgres_password psql -v ON_ERROR_STOP=1 -U postgres -h localhost -f $dir/initial.sql $verbose1"
else
  echo "${bold}Initial script is not found. Skipping initial script run${normal}"
fi

if [ $positive_enabled -eq 1 ]; then

echo "${bold}Testing positive sql scenarios in $dir/positive:${normal}"

for entry in "$dir"/positive/*.sql
do
  name=$(basename "$entry")
  eval "PGPASSWORD=postgres_password psql -v ON_ERROR_STOP=1 -U postgres -h localhost -c 'begin transaction;' -f $entry $verbose1 -c 'rollback transaction;'"
  retVal=$?
  if [ $retVal -eq 0 ]; then
    printf "${GREEN}SUCCESS${NC} ${bold}$name${normal}\n"
  else
    printf "${RED}FAILURE${NC} ${bold}$name${normal}\n"
    status="1"
    (( fail_count++ ))
  fi
  (( test_count++ ))
done

echo "${bold}Positive testing: succeeded $(( test_count - fail_count ))/$test_count ($fail_count failed)."
echo ""
echo "═════════════════════════════════════════"

else
  printf "${YELLOW}Note: positive testing is skipped${NC}\n"
fi

if [ $negative_enabled -eq 1 ]; then

test_count=0
fail_count=0
echo "${bold}Testing negative sql scenarios in $dir/negative:${normal}"

for entry in "$dir"/negative/*.sql
do
  name=$(basename "$entry")
  eval "PGPASSWORD=postgres_password psql -v ON_ERROR_STOP=1 -U postgres -h localhost -c 'begin transaction;' -f $entry $verbose2 -c 'rollback transaction;'"
  retVal=$?
  if  [ $retVal -ne 0 ]; then
    printf "${GREEN}SUCCESS${NC} ${bold}$name${normal}\n"
  else
    printf "${RED}FAILURE${NC} ${bold}$name${normal}\n"
    status="1"
    (( fail_count++ ))
  fi
  (( test_count++ ))
done

echo "${bold}Negative testing: succeeded $(( test_count - fail_count ))/$test_count ($fail_count failed)."

else
  printf "${YELLOW}Note: negative testing is skipped${NC}\n"
fi

eval "exit $status"
