<script lang="ts">
    import Header from '../../components/Header.svelte';
    import config from '../../../config.js';
    import { onMount } from 'svelte';
    import { getAuthHeader } from '../../components/Auth';

    let selectedCandidate: any

    let candidateSelection: boolean = true

    let error: boolean
    let errorMessage: string

    let saved: boolean

    async function downloadMedicalDocument() {
        try {
            const response = await fetch(config.host + '/candidates/' + selectedCandidate.id + '/medicalDocument', {
                method: 'GET',
                headers: {
                    "Accept": "application/pdf",
                    'Authorization': getAuthHeader() ?? ''
                }
            })

            if (response.ok) {
                let blob = await response.blob()
                var url = window.URL || window.webkitURL;
                let link = url.createObjectURL(blob);

                let a = document.createElement("a");
                a.setAttribute("download", `Medical document.pdf`);
                a.setAttribute("href", link);
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            } else {
              error = true
              errorMessage = 'Error occured during downloading'
            }
        } catch(ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during downloading'
        }
      }

    function cancelSelection() {
        candidateSelection = true
        error = false
        saved = false
    }

    function viewCandidate(candidate: any) {
        candidateSelection = false
        selectedCandidate = candidate
    }

    async function saveCandidate() {
        try {
            const response = await fetch(config.host + '/candidates/' + selectedCandidate.id, {
                method: 'PATCH',
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': getAuthHeader() ?? ''
                },
                body: JSON.stringify({
                    status: selectedCandidate.status
                }),
            })

            if (response.ok) {
                error = false
                saved = true
            } else {
                error = true
                errorMessage = 'Error occured during sending request'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during sending request'
        }
    }

    let candidates: any = []

    async function getCandidates() {
        try {
            const response = await fetch(config.host + '/candidates', {
                method: 'GET',
                headers: {
                    "Accept": "application/json",
                    'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                console.log('ok')
                candidates = await response.json();
            } else {
                error = true
                errorMessage = 'Error occured during getting candidates'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during getting candidates'
        }
    }

    onMount(async () => {
        await getCandidates()
	  })
</script>

<Header />

{#if candidateSelection}
<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <!-- Card -->
    <div class="flex flex-col">
      <div class="-m-1.5 overflow-x-auto">
        <div class="p-1.5 min-w-full inline-block align-middle">
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
            <!-- Header -->
            <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-b border-gray-200 dark:border-gray-700">
              <div>
                <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
                  Candidate requests
                </h2>
              </div>
            </div>

            {#if error}
                <div class="pt-6 w-full">
                    <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-5 sm:h-12" >
                        <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                        </svg>
                        {errorMessage}
                    </div>  
                </div>  
            {/if}
            <!-- End Header -->
  
            <!-- Table -->
            <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
              <thead class="bg-gray-50 dark:bg-slate-800">
                <tr>
                  <th scope="col" class="pl-6 py-3 text-left">
                    <label for="hs-at-with-checkboxes-main" class="flex hidden">
                      <input type="checkbox" class="shrink-0 border-gray-200 rounded text-blue-600 pointer-events-none focus:ring-blue-500 dark:bg-gray-800 dark:border-gray-700 dark:checked:bg-blue-500 dark:checked:border-blue-500 dark:focus:ring-offset-gray-800" id="hs-at-with-checkboxes-main">
                      <span class="sr-only">Checkbox</span>
                    </label>
                  </th>
  
                  <th scope="col" class="pl-6 lg:pl-3 xl:pl-0 pr-6 py-3 text-left">
                    <div class="flex items-center gap-x-2">
                      <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                        Photo
                      </span>
                    </div>
                  </th>
  
                  <th scope="col" class="px-6 py-3 text-left">
                    <div class="flex items-center gap-x-2">
                      <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                        First Name
                      </span>
                    </div>
                  </th>
  
                  <th scope="col" class="px-6 py-3 text-left">
                    <div class="flex items-center gap-x-2">
                      <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                        Last Name
                      </span>
                    </div>
                  </th>
  
                  <th scope="col" class="px-6 py-3 text-left">
                    <div class="flex items-center gap-x-2">
                      <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                        Phone
                      </span>
                    </div>
                  </th>

                  <th scope="col" class="px-6 py-3 text-left">
                    <div class="flex items-center gap-x-2">
                      <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                        Status
                      </span>
                    </div>
                  </th>
  
                  <th scope="col" class="px-6 py-3 text-right"></th>
                </tr>
              </thead>
  
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                {#each candidates as candidate}
                <tr>

                    <td class="h-px w-px whitespace-nowrap">
                        <div class="pl-6 py-3 hidden">
                          <label for="hs-at-with-checkboxes-1" class="flex hidden">
                          </label>
                        </div>
                      </td>
                  
                  <td class="h-px w-px whitespace-nowrap">
                    <div class="pl-6 lg:pl-3 xl:pl-0 pr-6 py-3">
                      <div class="flex items-center gap-x-3">
                        <img class="inline-block h-[2.375rem] w-[2.375rem] rounded-full" src="{candidate.photo}" alt="Empty">
                      </div>
                    </div>
                  </td>

                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <div class="grow">
                          <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{candidate.firstName}</span>
                        </div>
                    </div>
                  </td>


                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <div class="grow">
                          <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{candidate.lastName}</span>
                        </div>
                    </div>
                  </td>
                    
                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <span class="block text-sm text-gray-500">{candidate.phone}</span>
                    </div>
                  </td>

                  <td class="h-px w-px whitespace-nowrap">
                    <div class="px-6 py-3">
                        {#if candidate.status == 'Accepted'}
                        <span class="inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200">
                          <svg class="w-2.5 h-2.5" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                          </svg>
                          {candidate.status}
                        </span>
                        {:else if candidate.status == 'Rejected'}
                        <span class="inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                          <svg class="w-2.5 h-2.5" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                          </svg>
                          {candidate.status}
                        </span>
                        {:else}
                            <span class="inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-yellow-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                            <svg class="w-2.5 h-2.5" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                              <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                            {candidate.status}
                          </span>
                        {/if}
                    </div>
                  </td>

                  <td class="h-px w-px whitespace-nowrap">
                    <div class="px-6 py-1.5">
                      <a on:click={() => viewCandidate(candidate)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                        View
                      </a>
                    </div>
                  </td>
                </tr>
                {/each}
              </tbody>
            </table>
            <!-- End Table -->
  
            <!-- Footer -->
            <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-t border-gray-200 dark:border-gray-700">
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                  <span class="font-semibold text-gray-800 dark:text-gray-200">6</span> results
                </p>
              </div>
  
              <div>
                <div class="inline-flex gap-x-2">
                  <a class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border font-medium bg-white text-gray-700 shadow-sm align-middle hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-white focus:ring-blue-600 transition-all text-sm dark:bg-slate-900 dark:hover:bg-slate-800 dark:border-gray-700 dark:text-gray-400 dark:hover:text-white dark:focus:ring-offset-gray-800" href="#">
                    View all
                  </a>
                  
                  <button type="button" class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border font-medium bg-white text-gray-700 shadow-sm align-middle hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-white focus:ring-blue-600 transition-all text-sm dark:bg-slate-900 dark:hover:bg-slate-800 dark:border-gray-700 dark:text-gray-400 dark:hover:text-white dark:focus:ring-offset-gray-800">
                    <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                      <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
                    </svg>
                    Prev
                  </button>
  
                  <button type="button" class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border font-medium bg-white text-gray-700 shadow-sm align-middle hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-white focus:ring-blue-600 transition-all text-sm dark:bg-slate-900 dark:hover:bg-slate-800 dark:border-gray-700 dark:text-gray-400 dark:hover:text-white dark:focus:ring-offset-gray-800">
                    Next
                    <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                      <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
                    </svg>
                  </button>
                </div>
              </div>
            </div>
            <!-- End Footer -->
          </div>
        </div>
      </div>
    </div>
    <!-- End Card -->
    </div>

{:else}

<div class="max-w-[50rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col">
      <div class="-m-1.5 overflow-x-auto">
        <div class="p-1.5 min-w-full inline-block align-middle">
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
            <div class="px-6 py-4 grid gap-3  md:items-center border-b border-gray-200 dark:border-gray-700">
              <div class="border-b border-gray-900/10 pb-12">
                <h2 class="text-base font-semibold leading-7 text-gray-900">Candidate Request</h2>

                {#if error}
                  <div class="pt-6 w-full">
                      <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-5 sm:h-12" >
                          <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                          <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                          </svg>
                          {errorMessage}
                      </div>  
                  </div>  
                {/if}

                {#if saved}
                  <div class="pt-6 w-full">
                      <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-green-100 sm:mx-5 sm:h-12" >
                          Candidate request successfully updated!
                      </div>  
                  </div>  
                {/if}
  
                <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                <div class="sm:col-span-3">
                    <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">First name</label>
                    <div class="mt-2">
                    <input disabled bind:value={selectedCandidate.firstName} type="text" name="first-name" id="first-name" autocomplete="given-name" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>
  
                <div class="sm:col-span-3">
                    <label for="last-name" class="block text-sm font-medium leading-6 text-gray-900">Last name</label>
                    <div class="mt-2">
                    <input disabled bind:value={selectedCandidate.lastName} type="text" name="last-name" id="last-name" autocomplete="family-name" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>
        
                <div class="sm:col-span-3">
                    <label for="phone" class="block text-sm font-medium leading-6 text-gray-900">Phone</label>
                    <div class="mt-2">
                    <input disabled bind:value={selectedCandidate.phone} id="phone" name="phone" type="phone" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="123-45-678" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}">
                    </div>
                </div>

                    <div class="sm:col-span-3">
                        <label for="date of birth" class="block text-sm font-medium leading-6 text-gray-900">Date of birth</label>
                        <div class="mt-2">
                            <input disabled bind:value={selectedCandidate.dateOfBirth} datepicker type="text" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="Select date of birth">
                        </div>
                    </div>

                    <div class="sm:col-span-3">
                        <label for="sex" class="block text-sm font-medium leading-6 text-gray-900">Sex</label>
                        <div class="mt-2">
                            <select disabled bind:value={selectedCandidate.sex} id="countries" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                            <option selected>Choose a sex</option>
                            <option value="Female">Female</option>
                            <option value="Male">Male</option>
                            <option value="Transformer">Transformer</option>
                            </select>
                        </div>
                    </div>

                    <div class="sm:col-span-3">
                        <label for="race" class="block text-sm font-medium leading-6 text-gray-900">Race</label>
                        <div class="mt-2">
                            <select disabled bind:value={selectedCandidate.race} id="countries" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                            <option selected>Choose a race</option>
                            <option value="White">White supreme</option>
                            <option value="Black">Black</option>
                            <option value="Asian">Asian</option>
                            </select>
                        </div>
                    </div>

                    <div class="sm:col-span-3">
                        <label for="height" class="block text-sm font-medium leading-6 text-gray-900">Height</label>
                        <div class="mt-2">
                        <input disabled bind:value={selectedCandidate.height} type="text" name="height" id="height" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                        </div>
                    </div>

                    <div class="sm:col-span-3">
                        <label for="weight" class="block text-sm font-medium leading-6 text-gray-900">Weight</label>
                        <div class="mt-2">
                        <input disabled bind:value={selectedCandidate.weight} type="text" name="weight" id="weight" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                        </div>
                    </div>

                <div class="col-span-full">
                    <label for="street-address" class="block text-sm font-medium leading-6 text-gray-900">Address</label>
                    <div class="mt-2">
                    <input disabled bind:value={selectedCandidate.address} type="text" name="street-address" id="street-address" autocomplete="street-address" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>

                <div class="col-span-full">
                    <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Describe yourself</label>
                    <div class="mt-2">
                    <textarea disabled bind:value={selectedCandidate.description} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                    </div>
                </div>
                </div>
            </div>

            <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                <div class="sm:col-span-3">
                    <label for="photo" class="block text-sm font-medium leading-3 text-gray-900">Photo</label>
                    <div class="mt-2">
                        <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                        <div class="text-center">
                            <img class="inline-block " src="{selectedCandidate.photo}" alt="Image Description">
                        </div>
                        </div>      
                    </div>
                </div>

                <div class="sm:col-span-3">
                    <label for="med" class="block text-sm font-medium leading-3 text-gray-900">Medical document</label>
                    <div class="mt-2">
                        <li class="flex items-center justify-between py-4 pl-4 pr-5 text-sm leading-6">
                            <div class="flex w-0 flex-1 items-center">
                              <svg class="h-5 w-5 flex-shrink-0 text-gray-400" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                <path fill-rule="evenodd" d="M15.621 4.379a3 3 0 00-4.242 0l-7 7a3 3 0 004.241 4.243h.001l.497-.5a.75.75 0 011.064 1.057l-.498.501-.002.002a4.5 4.5 0 01-6.364-6.364l7-7a4.5 4.5 0 016.368 6.36l-3.455 3.553A2.625 2.625 0 119.52 9.52l3.45-3.451a.75.75 0 111.061 1.06l-3.45 3.451a1.125 1.125 0 001.587 1.595l3.454-3.553a3 3 0 000-4.242z" clip-rule="evenodd" />
                              </svg>
                              <div class="ml-4 flex min-w-0 flex-1 gap-2">
                                <span class="truncate font-medium">MedicalDocument.pdf</span>
                              </div>
                            </div>
                            <div class="ml-4 flex-shrink-0">
                              <a on:click={downloadMedicalDocument} href="#" download="Medical document" class="font-medium text-indigo-600 hover:text-indigo-500">Download</a>
                            </div>
                          </li>
                    </div>
                </div>
            </div>
            </div>
  
            <div class="px-6 py-4 grid gap-3  md:items-center border-b border-gray-200 dark:border-gray-700">
                <h2 class="text-base font-semibold leading-7 text-gray-900">Request Status</h2>
                <fieldset>
                    <div class="mt-2 space-y-5">
                        <div class="flex inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200">
                        <input id="push-everything" name="push-notifications" type="radio" class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600">
                        <label for="push-everything" class="block text-sm font-medium leading-6 text-gray-900">Accepted</label>
                        </div>
                        <div class="flex inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                        <input id="push-email" name="push-notifications" type="radio" class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600">
                        <label for="push-email" class="block text-sm font-medium leading-6 text-gray-900">Rejected</label>
                        </div>
                        <div class="flex inline-flex items-center gap-1.5 py-0.5 px-2 rounded-full text-xs font-medium bg-yellow-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                        <input id="push-nothing" name="push-notifications" type="radio" class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600">
                        <label for="push-nothing" class="block text-sm font-medium leading-6 text-gray-900">Pending</label>
                        </div>
                    </div>
                </fieldset>
            </div>

            <div class="mt-6 flex items-center justify-end gap-x-5 px-4 py-5 sm:px-6 lg:px-5 lg:py-5">
                <button on:click={cancelSelection} type="button" class="text-sm font-semibold leading-6 text-gray-900">Cancel</button>
                <button on:click={saveCandidate} type="button" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Save</button>
            </div>
        </div>
    </div>
    </div>
</div>
</div>

{/if}
