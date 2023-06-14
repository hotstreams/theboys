<script lang="ts">
    import { onMount } from "svelte";

    let error: boolean
    let errorMessage: string

    export let selectedResearch: any
</script>

<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
   <div class="flex flex-col py-10">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
                    <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-b border-gray-200 dark:border-gray-700">
                        <div>
                            <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
                                Medicine
                            </h2>
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
                                            Doze ML
                                        </span>
                                    </div>
                                </th>

                                <th scope="col" class="px-6 py-3 text-left">
                                    <div class="flex items-center gap-x-2">
                                        <span class="text-xs font-semibold uppercase tracking-wide text-gray-800 dark:text-gray-200">
                                            Doze count
                                        </span>
                                    </div>
                                </th>
                            </tr>
                        </thead>

                        <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                            <tr>
                                <td class="h-px w-px whitespace-nowrap">
                                    <div class="pl-6 py-3 hidden">
                                        <label for="hs-at-with-checkboxes-1" class="flex hidden"></label>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{selectedResearch.medicine}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{selectedResearch.dozeMl}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{selectedResearch.dozeCount}</span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>