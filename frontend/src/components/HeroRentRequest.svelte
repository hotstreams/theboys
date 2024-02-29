<script lang="ts">
    import { goto } from '$app/navigation';
    import config from '../../config.js';
    import { getAuthHeader } from './Auth.js'

    export let heroSelection: boolean
    export let requestAnyHero: boolean
    export let selectedHero: any

    let heroDescription: string
    let requestDescription: string
    let dateStart: string
    let dateEnd: string

    let validationError: boolean
    let validationMesssage: string

    function validate() {
      dateStart = document.getElementsByName("start")[0].getAttribute("value") ?? ""
      dateEnd = document.getElementsByName("end")[0].getAttribute("value") ?? ""
      if (!requestAnyHero && (requestDescription == null || dateStart == null || dateEnd == null)) {
          validationError = true
          validationMesssage = 'All fields should be filled out'
          return false
      } 

      if (requestAnyHero && (requestDescription == null || heroDescription == null || dateStart == null || dateEnd == null)) {
          validationError = true
          validationMesssage = 'All fields should be filled out'
          return false
      }  

      if (!/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/.test(dateStart) || !/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/.test(dateEnd)) {
          console.log(dateStart)
          console.log(dateEnd)
          validationError = true
          validationMesssage = 'Dates should be XXXX-XX-XX'
          return false
      }

      validationError = false
      return true
    }

    function cancelRequest() {
      heroSelection = true
    }

    async function sendRequest() {
        if (!validate()) {
            return
        }

        try {
          const res = await fetch(config.host + '/heroes/rent', {
              method: 'POST',
              body: JSON.stringify({
                  heroId: selectedHero.id,
                  heroDescription,
                  requestDescription,
                  dateStart,
                  dateEnd
              }),
              headers: {
                'Content-Type': "application/json",
                'Authorization': getAuthHeader() ?? ''
              }
          })

          if (res.ok) {
            goto('/rent-requests')
          } else {
            validationError = true
            validationMesssage = 'Error occured during sending request'
          }

        } catch (error) {
            console.log(error)
            validationError = true
            validationMesssage = 'Error occured during sending request'

        }
    }

    let stars = [
      1, 2, 3, 4, 5
    ]
</script>


    <div class="max-w-[50rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
      <div class="flex flex-col">
        <div class="-m-1.5 overflow-x-auto">
          <div class="p-1.5 min-w-full inline-block align-middle">
            <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
              <div class="px-6 py-4 grid gap-3 md:items-center border-b border-gray-200 dark:border-gray-700">
                <div>
                          <div class="space-y-12">
                            <div class="border-b border-gray-900/10 pb-12">
                              <h2 class="text-base font-semibold leading-7 text-gray-900">Request for a hero</h2>
                              <p class="mt-1 text-sm leading-6 text-gray-600">This information will be send to the manager to process your desires.</p>
                        
                              {#if validationError}
                              <div class="pt-6 w-full">
                                  <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-5 sm:h-12" >
                                      <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                                      <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                                      </svg>
                                      {validationMesssage}
                                  </div>  
                              </div>  
                              {/if}

                           {#if !requestAnyHero}
                              <table class="mt-5 min-w-full divide-y divide-gray-200 dark:divide-gray-700">
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
                                          Name
                                        </span>
                                      </div>
                                    </th>

                                    <th scope="col" class="px-6 py-3 text-left">
                                      <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                          Rating
                                        </span>
                                      </div>
                                    </th>

                                    <th scope="col" class="px-6 py-3 text-left">
                                      <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                          Abilities
                                        </span>
                                      </div>
                                    </th>

                                    <th scope="col" class="px-6 py-3 text-left">
                                      <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                          Description
                                        </span>
                                      </div>
                                    </th>
                                  </tr>
                                </thead>

                                <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
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
                                          <!-- <img class="inline-block h-[2.375rem] w-[2.375rem] rounded-full" src="{hero.image}" alt="Image Description"> -->
                                          <img class="inline-block h-[2.375rem] w-[2.375rem] rounded-full" src="https://images.unsplash.com/photo-1568602471122-7832951cc4c5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=facearea&facepad=2&w=300&h=300&q=80" alt="Image Description">

                                          <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{selectedHero.name}</span>
                                          </div>
                                        </div>
                                      </div>
                                    </td>

                                    <td class="h-px w-72 whitespace-nowrap">
                                      <div class="px-6 py-3">
                                        <div class="flex items-center">
                                          {#each stars as star}
                                            {#if selectedHero.rating > star}
                                              <svg aria-hidden="true" class="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>First star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                                            {:else}
                                              <svg aria-hidden="true" class="w-5 h-5 text-gray-300 dark:text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                                            {/if}
                                          {/each}
                                          <p class="ml-2 text-sm font-medium text-gray-500 dark:text-gray-400">{selectedHero.rating} out of 5</p>
                                        </div>
                                      </div>
                                    </td>

                                    <td class="h-px w-72 whitespace-nowrap">
                                      <div class="px-6 py-3">
                                        <span class="block text-sm text-gray-500">{selectedHero.abilities}</span>
                                      </div>
                                    </td>

                                    <td class="h-px w-72 whitespace-nowrap">
                                      <div class="px-6 py-3">
                                        <span class="block text-sm text-gray-500">{selectedHero.description}</span>
                                      </div>
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            {:else}
                            <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                              <div class="col-span-full">
                                <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Hero description</label>
                                <div class="mt-2">
                                  <textarea bind:value={heroDescription} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                </div>
                              </div>
                            </div>
                            {/if}

                              <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                                <div class="col-span-full">
                                  <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Request description</label>
                                  <div class="mt-2">
                                    <textarea bind:value={requestDescription} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                  </div>
                                </div>
                              </div>
                            </div>
                        
                            <div class="border-b border-gray-900/10 pb-12">
                              <h2 class="text-base font-semibold leading-7 text-gray-900">Date</h2>
                              <p class="mt-1 text-sm leading-6 text-gray-600 hodde"></p>
                      
                              <div date-rangepicker class="mt-10 flex items-center">
                                <div class="relative">
                                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                      <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                                  </div>
                                  <input bind:value={dateStart} name="start" type="date" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Date start XXXX-XX-XX">
                                </div>
                                <span class="mx-4 text-gray-500">to</span>
                                <div class="relative">
                                  <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                      <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                                  </div>
                                  <input bind:value={dateEnd} name="end" type="date" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Date end XXXX-XX-XX">
                                </div>
                              </div> 
                              
                            </div>
                            
                          </div>
                        
                          <div class="mt-6 flex items-center justify-end gap-x-6">
                            <button on:click={cancelRequest} class="text-sm font-semibold leading-6 text-gray-900">Cancel</button>
                            <button on:click={sendRequest} class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Send</button>
                          </div>
                  </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>