<script lang="ts">
    import CandidateList from "./CandidateList.svelte"
    import MedicineList from "./MedicineList.svelte"
    import TestList from "./TestList.svelte"
    import AssesmentList from "./AssesmentList.svelte"
    import { createEventDispatcher } from 'svelte';
    import { saveResearch } from "./Reseaches";
    import CandidateSelected from "./CandidateSelected.svelte";
    import MedicineSelected from "./MedicineSelected.svelte";

    export let selectedResearch: any

    let error: boolean = false
    let errorMessage: string

    let hideAllExceptTestList: boolean = false
    let hideAllExceptAssesmentList: boolean = false

    const dispatch = createEventDispatcher()

    function cancelResearch() {
        dispatch('cancel')
    }

    async function updateResearch() {
       const res = await saveResearch(selectedResearch.researchId, selectedResearch.status)
       if (res.error) {
            error = true
            errorMessage = res.errorMessage ?? ''
       } else {
            dispatch('cancel')
       } 
    }
</script>

{#if !hideAllExceptTestList && !hideAllExceptAssesmentList}
    <CandidateSelected bind:selectedCandidate={selectedResearch.candidate} />
    <MedicineSelected selectedResearch={selectedResearch}/>
{/if}

{#if !hideAllExceptAssesmentList}
    <TestList on:hide={() => hideAllExceptTestList = true} on:unhide={() => {hideAllExceptTestList = false; }} bind:selectedResearch={selectedResearch} on:up={() => dispatch('up')}/>
{/if}

{#if !hideAllExceptTestList}
    <AssesmentList on:hide={() => hideAllExceptAssesmentList = true} on:unhide={() => hideAllExceptAssesmentList = false} bind:selectedResearch={selectedResearch} on:up={() => dispatch('up')}/>
{/if}

{#if !hideAllExceptTestList && !hideAllExceptAssesmentList}
<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col py-10">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900">
                    <div class="px-6 py-4 grid gap-3 md:flex justify-begin md:items-center border-b border-gray-200">
                        <div class="sm:col-span-3">
                            <label for="status" class="block text-xl font-semibold text-gray-800 leading-6 text-gray-900">Research Status</label>
                            <div class="mt-2">
                                <select bind:value={selectedResearch.status} id="status" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                    <option value="INITIALIZED">INITIALIZED</option>
                                    <option value="CANDIDATE_FOUND">CANDIDATE_FOUND</option>
                                    <option value="WAITING_FOR_INJECTION">WAITING_FOR_INJECTION</option>
                                    <option value="WAITING_FOR_TESTS">WAITING_FOR_TESTS</option>
                                    <option value="WAITING_FOR_ASSESSMENT">WAITING_FOR_ASSESSMENT</option>
                                    <option value="WAITING_FOR_EXAM">WAITING_FOR_EXAM</option>
                                    <option value="CANDIDATE_BECAME_HERO">CANDIDATE_BECAME_HERO</option>
                                    <option value="CANDIDATE_DIED_AT_INJECTION">CANDIDATE_DIED_AT_INJECTION</option>
                                    <option value="CANDIDATE_DIED_AT_ASSESSMENT">CANDIDATE_DIED_AT_ASSESSMENT</option>
                                    <option value="CANDIDATE_DIED_AT_EXAM">CANDIDATE_DIED_AT_EXAM</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
{/if}

{#if !hideAllExceptTestList && !hideAllExceptAssesmentList}
<div class="max-w-[70rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col py-10">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900">
                    <div class="px-6 py-4 grid gap-3 md:flex justify-end md:items-center border-b border-gray-200">
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

                        <div class="px-6 py-1.5">
                            <a on:click={cancelResearch} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-gray-500 text-white hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                Cancel
                            </a>
                        </div>
                        <div class="px-3 py-1.0">
                            <a on:click={updateResearch} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                                Save research
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
{/if}