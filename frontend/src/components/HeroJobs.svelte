<script lang="ts">
    import config from '../../config.js';
    import { getAuthHeader } from './Auth.js'
    import { onMount } from "svelte";


    let requests: any = []

    let error: boolean
    let errorMessage: string

    async function changeStatus(request: any, status: any) {
      try {
            const response = await fetch(config.host + '/entrepreneurs/' + request.enterpreneurId + '/rents/' + request.orderId, {
                method: 'PATCH',
                body: JSON.stringify({
                  status: status
                }),
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                request.status = status
            } else {
                error = true
                errorMessage = 'Error occured during request'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during request'
        }
    }

    async function confirm(request: any) {
        await changeStatus(request, 'HERO_CONFIRMED')
    }

    async function decline(request: any) {
        await changeStatus(request, 'HERO_DECLAINED')
    }

    async function getJobs() {
        try {
            const response = await fetch(config.host + '/heroes/rents', {
                method: 'GET',
                headers: {
                    "Accept": "application/json",
					'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                requests = await response.json();
            } else {
                error = true
                errorMessage = 'Error occured during getting jobs'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during getting jobs'
        }
    }

    onMount(async () => {
      await getJobs()
    })
</script>


    <div class="max-w-[50rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
      <div class="flex flex-col">
        <div class="-m-1.5 overflow-x-auto">
          <div class="p-1.5 min-w-full inline-block align-middle">
            <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
              <div class="px-6 py-4 grid gap-3 md:items-center border-b border-gray-200 dark:border-gray-700">
                <div>
                          <div class="space-y-3">
                            <div class="border-b border-gray-900/10 pb-12">
                              <h1 class="text-xl font-semibold leading-7 text-gray-900">Job requests</h1>
                        
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
                          </div>

                          {#each requests as request}
                            <div class="border-b border-gray-900/10 pb-8">
                                <h2 class="text-xl font-semibold text-gray-900">From {request.name}</h2>

                                <div class="mt-2">
                                    <h2 class="text-base text-gray-900">Date</h2>
                                    <div class="mt-2">
                                    <input disabled bind:value={request.dateStart} name="start" type="date" class="w-50 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Date start XXXX-XX-XX">
                                    <span class="mx-4 text-gray-500">to</span>
                                    <input disabled bind:value={request.dateEnd} name="end" type="date" class="w-50 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Date end XXXX-XX-XX">
                                    </div>
                                </div>
                                <div class="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                                    <div class="col-span-full">
                                      <label for="about" class="block text-base font-medium leading-6 text-gray-900">Request description</label>
                                      <div class="mt-2">
                                        <textarea disabled bind:value={request.requestDescription} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                      </div>
                                    </div>
                                </div>
                                <div class="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                                    <div class="col-span-full">
                                      <label for="about" class="text-base font-medium leading-6 text-gray-900">Request status</label>
                                      <span class="mt-4 inline-flex items-center gap-1.5 py-0.5 px-5 rounded-full text-xs font-medium bg-yellow-100 text-red-800 dark:bg-red-900 dark:text-red-200">
                                        <svg class="w-2.5 h-2.5" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                                          <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                        </svg>
                                        {request.status}
                                      </span>
                                    </div>
                                </div>
                                <div class="mt-3 flex items-center justify-end gap-x-6">
                                    <button on:click={()=> decline(request)} class="text-sm font-semibold leading-6 text-gray-900">Decline</button>
                                    <button on:click={()=> confirm(request)} class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Confirm</button>
                                </div>
                            </div>
                          {/each}
                        </div>
                  </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>