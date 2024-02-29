<script lang="ts">
    import { onMount } from "svelte";
    import config from '../../config.js';
    import { getAuthHeader, isAuthenticated } from './Auth.js'
    import { createEventDispatcher } from 'svelte';

    let dispatch = createEventDispatcher()

    export let heroSelection: boolean
    export let selectedHero: any

    let heroes: any = []

    let error: boolean
    let errorMessage: string

    function viewHero(hero: any) {
        heroSelection = false
        selectedHero = hero
        dispatch('view')
    }

    async function scoreHero(hero: any) {
        if (hero.userRate == null || hero.userRate == "" || parseInt(hero.userRate) < 1 || parseInt(hero.userRate) > 5) {
            error = true
            errorMessage = "User rating should be in rage [1, 5]"
            return
        }  

        error = false;
        errorMessage = ""
        
        await sendScore(hero)
    }

    async function sendScore(hero: any) {
        try {
            const response = await fetch(config.host + '/heroes/' + hero.id + '/rate?rate=' + hero.userRate, {
                method: 'POST',
                headers: {
                    "Accept": "application/json",
					'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                error = false
                await getHeroes()
            } else {
                error = true
                errorMessage = 'Error occured during rating update'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during rating update'
        }
    }

    async function subscribeToHero(hero: any) {
        try {
            const response = await fetch(config.host + '/posts/subscriptions/' + hero.id, {
                method: 'POST',
                headers: {
                    "Accept": "application/json",
					          'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                error = false
                await getHeroes()
            } else {
                error = true
                errorMessage = 'Error occured during subscription'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during subscription'
        }
    }

    async function unsubscribeToHero(hero: any) {
        try {
            const response = await fetch(config.host + '/posts/subscriptions/' + hero.id, {
                method: 'DELETE',
                headers: {
                    "Accept": "application/json",
					          'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                error = false
                await getHeroes()
            } else {
                error = true
                errorMessage = 'Error occured during unsubscription'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during unsubscription'
        }
    }

    async function getHeroes() {
        try {
            const response = await fetch(config.host + '/heroes', {
                method: 'GET',
                headers: {
                    "Accept": "application/json",
					'Authorization': getAuthHeader() ?? ''
                }
            });

            if (response.ok) {
                heroes = await response.json();
            } else {
                error = true
                errorMessage = 'Error occured during getting heroes'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during getting heroes'
        }
    }

    let stars = [
      1, 2, 3, 4, 5
    ]

    onMount(async () => {
      await getHeroes()
    })
</script>

<div class="max-w-[100rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <!-- Card -->
    <div class="flex flex-col">
      <div class="-m-1.5 overflow-x-auto">
        <div class="p-1.5 min-w-full inline-block align-middle">
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
            <!-- Header -->
            <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-b border-gray-200 dark:border-gray-700">
              <div>
                <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
                  Heroes
                </h2>
              </div>
  
              <!-- <div>
                <div class="inline-flex gap-x-2">
                  <a on:click={requestAny} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                    <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="none">
                      <path d="M2.63452 7.50001L13.6345 7.5M8.13452 13V2" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg> -->
                    <!-- Request any -->
                  <!-- </a> -->
                <!-- </div> -->
              <!-- </div> --> 
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
                  
                  <th scope="col" class="px-6 py-3 text-right"></th>

                  {#if isAuthenticated()}
                    <th scope="col" class="px-6 py-3 text-right"></th>  
                    <th scope="col" class="px-6 py-3 text-right"></th> 
                    <th scope="col" class="px-6 py-3 text-right"></th>               
                  {/if}
                </tr>
              </thead>
  
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                {#each heroes as hero}
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
                        <!-- <img class="inline-block h-[2.375rem] w-[2.375rem] rounded-full" src="{hero.img}" alt="Image Description"> -->
                        <img class="inline-block h-[2.375rem] w-[2.375rem] rounded-full ring-2 ring-white dark:ring-gray-800" src="https://images.unsplash.com/photo-1568602471122-7832951cc4c5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=facearea&facepad=2&w=320&h=320&q=80" alt="Image Description">

                        <div class="grow">
                          <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{hero.name}</span>
                        </div>
                      </div>
                    </div>
                  </td>

                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <div class="flex items-center">
                        {#each stars as star}
                          {#if hero.rating >= star}
                            <svg aria-hidden="true" class="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>First star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                          {:else}
                            <svg aria-hidden="true" class="w-5 h-5 text-gray-300 dark:text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                          {/if}
                        {/each}
                        <p class="ml-2 text-sm font-medium text-gray-500 dark:text-gray-400">{hero.rating} out of 5</p>
                      </div>
                    </div>
                  </td>

                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <span class="block text-sm text-gray-500">{hero.abilities}</span>
                    </div>
                  </td>

                  <td class="h-px w-72 whitespace-nowrap">
                    <div class="px-6 py-3">
                      <span class="block text-sm text-gray-500">{hero.description}</span>
                    </div>
                  </td>

                  <td class="h-px w-px whitespace-nowrap">
                    <div class="px-6 py-1.5">
                      <a on:click={() => viewHero(hero)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                        View
                      </a>
                    </div>
                  </td>

                  {#if isAuthenticated()}

                    {#if hero.alreadyRatedByUser == false}
                        <td class="h-px w-px whitespace-nowrap">
                          <input bind:value={hero.userRate} type="number" min="1" max="5" name="score" id="last-name" autocomplete="family-name" class="block w-20 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                        </td>

                        <td class="h-px w-px whitespace-nowrap">
                            <div class="px-6 py-1.5">
                            <a on:click={() => scoreHero(hero)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                Score
                            </a>
                            </div>
                        </td>  
                    {/if} 

                    {#if hero.alreadyRatedByUser == true}
                        <td class="h-px w-px whitespace-nowrap"></td>
                        <td class="h-px w-px whitespace-nowrap"></td>  
                    {/if} 
                  
                    {#if hero.subscribed == false}
                        <td class="h-px w-px whitespace-nowrap">
                            <div class="px-6 py-1.5">
                            <a on:click={() => subscribeToHero(hero)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                Like, subscribe
                            </a>
                            </div>
                        </td> 
                    {:else}
                    <td class="h-px w-px whitespace-nowrap">
                        <div class="px-6 py-1.5">
                        <a on:click={() => unsubscribeToHero(hero)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                            Dislike, unsubscribe
                        </a>
                        </div>
                    </td> 
                    {/if} 
        
                     
                  {/if}

                </tr>
                {/each}
              </tbody>
            </table>
            <!-- End Table -->
  
            <!-- Footer -->
            <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-t border-gray-200 dark:border-gray-700">
              <div>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                  <span class="font-semibold text-gray-800 dark:text-gray-200">{heroes.length}</span> results
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