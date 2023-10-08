<script lang="ts">
    import { onMount } from 'svelte';
    import { getMedicines } from './Medicines';

    let error: boolean
    let errorMessage: string
    
    let medicines: any = []

    export let selectedMedicine: any

    function selectMedicine(medicine: any) {
        selectedMedicine = medicine
    }

    onMount(async () => {
        const med = await getMedicines()
        if (med.error) {
            error = true
            errorMessage = med.errorMessage ?? ''
        } else {
            medicines = med.medicines
        }
    })
</script>

<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
   <div class="flex flex-col py-10">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
                    <div class="px-6 py-4 grid gap-3 md:flex md:justify-between md:items-center border-b border-gray-200 dark:border-gray-700">
                        <div>
                            <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
                                Select medicine
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

                                <th scope="col" class="px-6 py-3 text-right"></th>
                            </tr>
                        </thead>

                        <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                            {#each medicines as medicine}
                            <tr>
                                <td class="h-px w-px whitespace-nowrap">
                                    <div class="pl-6 py-3 hidden">
                                        <label for="hs-at-with-checkboxes-1" class="flex hidden"></label>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <div class="grow">
                                            <span class="block text-sm font-semibold text-gray-800 dark:text-gray-200">{medicine.name}</span>
                                        </div>
                                    </div>
                                </td>

                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <select bind:value={medicine.dozeMl} id="dozeMl {medicine.id}" class="block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                            <option selected>10</option>
                                            <option>30</option>
                                            <option>50</option>
                                        </select>
                                    </div>
                                </td>
            
                                <td class="h-px w-72 whitespace-nowrap">
                                    <div class="px-6 py-3">
                                        <select bind:value={medicine.dozeCount} id="dozeCount {medicine.id}" class="block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                            <option selected>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                        </select>
                                    </div>
                                </td>

                                {#if selectedMedicine == medicine}
                                    <td class="h-px w-px whitespace-nowrap">
                                    <div class="px-6 py-1.5">
                                        <a on:click={() => selectMedicine(medicine)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-900 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                        Select
                                        </a>
                                    </div>
                                    </td>
                                {:else}
                                    <td class="h-px w-px whitespace-nowrap">
                                    <div class="px-6 py-1.5">
                                        <a on:click={() => selectMedicine(medicine)} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                        Select
                                        </a>
                                    </div>
                                    </td>
                                {/if}
                            </tr>
                            {/each}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>