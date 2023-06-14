<script lang="ts">
    import { onMount } from 'svelte';
    import AssesmentAdd from './AssesmentAdd.svelte';
    import { createEventDispatcher } from 'svelte';
    import AssesmentView from './AssesmentView.svelte';
    
    export let selectedResearch: any
    export let selectedAssesment: any = {}

    let showAddAssesment: boolean = false
    let showViewAssesment: boolean = false

    let error: boolean
    let errorMessage: string

    let saved: boolean

    const dispatch = createEventDispatcher()

    function viewAssesment(assesment: any) {
        selectedAssesment = assesment
        showViewAssesment = true
        dispatch('hide')
    }

    function viewAddAssesment() {
        selectedAssesment = { }
        showAddAssesment = true
        dispatch('hide')
    }

    function cancelAddAssesment() {
        showAddAssesment = false
        dispatch('unhide')
    }

    function cancelViewAssesment() {
        showViewAssesment = false
        dispatch('unhide')
    }
</script>

{#if showAddAssesment}
    <AssesmentAdd selectedAssesment={selectedAssesment} on:cancel={cancelAddAssesment} selectedResearch={selectedResearch} on:up={() => dispatch('up')}/>
{/if}

{#if showViewAssesment}
    <AssesmentView selectedAssesment={selectedAssesment} on:cancel={cancelViewAssesment} selectedResearch={selectedResearch}/>
{/if}

{#if !showAddAssesment && !showViewAssesment}
<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
                    <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-b border-gray-200 dark:border-gray-700">
                        <div>
                            <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
                                Assesments
                            </h2>
                        </div>

                        <div>
                            <div class="inline-flex gap-x-2">
                                <a on:click={viewAddAssesment} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm" href="#">
                                    Add Assesment
                                </a>
                            </div>
                        </div>
                    </div>

                    {#if error}
                        <div class="pt-6 pb-6 w-full">
                            <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-5 sm:h-12" >
                                <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                                </svg>
                                {errorMessage}
                            </div>  
                        </div>  
                    {/if}
  
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
                                            Request date
                                        </span>
                                    </div>
                                </th>
                
                                <th scope="col" class="px-6 py-3 text-left">
                                    <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                            Event date
                                        </span>
                                    </div>
                                </th>
                
                                <th scope="col" class="px-6 py-3 text-left">
                                    <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                            Comment
                                        </span>
                                    </div>
                                </th>

                                <th scope="col" class="px-6 py-3 text-left">
                                    <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                            Passed
                                        </span>
                                    </div>
                                </th>

                                <th scope="col" class="px-6 py-3 text-right"></th>
                            </tr>
                        </thead>
                
                        <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                            {#each selectedResearch.assessments as assesment}
                            <tr>
                                <td class="h-px w-px whitespace-nowrap">
                                    <div class="pl-6 py-3 hidden">
                                    <label for="hs-at-with-checkboxes-1" class="flex hidden">
                                    </label>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-3 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{assesment.name}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{assesment.requestDate}</span>
                                        </div>
                                    </div>
                                </td>
                                
                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{assesment.eventDate ?? ''}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{assesment.comment ?? ''}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{assesment.passed}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-px whitespace-nowrap">
                                    <div class="px-6 py-1.5">
                                      <a on:click={() => viewAssesment(assesment)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                        View
                                      </a>
                                    </div>
                                </td>
                            </tr>
                            {/each}
                        </tbody>
                    </table>
  
                    <!-- <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-t border-gray-200 dark:border-gray-700">
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
                    </div> -->
                </div>
            </div>
        </div>
    </div>
</div>
{/if}