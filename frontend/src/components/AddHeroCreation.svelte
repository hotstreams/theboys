<script lang="ts">
    import Header from './Header.svelte';
    import config from '../../config.js';
    import { getAuthHeader, getUser } from './Auth.js'

    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher()

    let description: string

    let validation: boolean = false
    let validationMsg: string

    function validate() {
      if (description == null) {
        validation = true
        validationMsg = 'Description cannot be empty'
        return false
      }

        validation = false
        return true
    }

    async function send() {
      if (!validate()) {
          return
      } 

      try {
        const res = await fetch(config.host + '/managers/rents/update', {
            method: 'POST',
            body: JSON.stringify({
                description
            }),
            headers: {
              'Content-Type': "application/json",
              'Authorization': getAuthHeader() ?? ''
            }
        })

        if (res.ok) {
            dispatch('cancel');
        } else {
            validation = true
            validationMsg = 'Error occured during sending your request'
        }
      } catch (error) {
          console.log(error)
          validation = true
          validationMsg = 'Error occured during sending your request'
      }
    }
</script>

<Header />

<div class="max-w-[50rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col">
      <div class="-m-1.5 overflow-x-auto">
        <div class="p-1.5 min-w-full inline-block align-middle">
          <div class="bg-white rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
            <div class="px-6 py-4 grid gap-3  md:items-center  dark:border-gray-700">
              <div class="border-b border-gray-900/10 pb-12">
                <h2 class="text-base font-semibold leading-7 text-gray-900">Add hero creation request</h2>
  
                {#if validation}
                <div class="pt-6 w-full">
                    <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-5 sm:h-12" >
                        <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                        </svg>
                        {validationMsg}
                    </div>  
                </div>  
                {/if}

        <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
          <div class="col-span-full">
            <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Description</label>
            <div class="mt-2">
              <textarea bind:value={description} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
            </div>
          </div>
        </div>
      </div>

    </div>

    <div class="px-6 py-4 grid gap-3 md:flex justify-end md:items-center border-b border-gray-200">

            <div class="px-6 py-1.5">
                <a on:click={()=>dispatch('cancel')} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-gray-500 text-white hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                    Cancel
                </a>
            </div>
            <div class="px-3 py-1.0">
                <a on:click={send} class="py-2 px-3 inline-flex justify-center items-center gap-2 rounded-md border border-transparent font-semibold bg-blue-500 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-all text-sm dark:focus:ring-offset-gray-800" href="#">
                    Request
                </a>
            </div>
        </div>

    </div>
</div>
</div>
</div>
</div>