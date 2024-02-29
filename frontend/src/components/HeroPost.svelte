<script lang="ts">
    import config from '../../config.js';
    import { getAuthHeader } from './Auth.js'

    let error: boolean
    let errorMessage: string

    let title: string = ""
    let description: string = ""

    let created: boolean = false

    function sleep(ms: any) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    function validate() {
        if (title == null || title == "") {
            error = true
            errorMessage = "Title should not be empty!"
            return false
        }

        if (description == null || description == "") {
            error = true
            errorMessage = "Description should not be empty!"
            return false
        }

        error = false
        errorMessage = ""
        return true
    }

    async function submitPost() {
        if (!validate()) {
            return
        }

        await createPost()
    }

    async function createPost() {
        try {
            const response = await fetch(config.host + '/posts', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
					'Authorization': getAuthHeader() ?? ''
                },
                body: JSON.stringify({
                    title: title,
                    description: description
                })
            });

            if (response.ok) {
                created = true
                await sleep(2000);
                created = false
            } else {
                error = true
                errorMessage = 'Error occured during creating post'
            }
        } catch (ex) {
            console.log(ex)
            error = true
            errorMessage = 'Error occured during creating post'
        }
    }
</script>

<div class="max-w-[50rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
    <div class="flex flex-col">
        <div class="-m-1.5 overflow-x-auto">
            <div class="p-1.5 min-w-full inline-block align-middle">
                <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
                    <div class="px-6 py-4 grid gap-3  md:items-center border-b border-gray-200 dark:border-gray-700">

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

                        {#if created}
                        <div class="pt-6 w-full">
                            <div class="mx-8 flex flex-shrink-0 items-center justify-center rounded-full bg-green-100 sm:mx-5 sm:h-12" >
                                Post was successfully created!
                            </div>  
                        </div>  
                        {/if}
                        
                        <div class="border-b border-gray-900/10 pb-12">
                            <h2 class="text-xl text-base font-semibold leading-7 text-gray-900">Post creation</h2>

                            <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-1">
                                    <div class="sm:col-span-1">
                                        <label for="name" class="block text-sm font-medium leading-6 text-gray-900">Title</label>
                                        <div class="mt-2">
                                            <input bind:value={title} type="text" name="name" id="name" autocomplete="family-name" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                        </div>
                                    </div>

                                    <div class="sm:col-span-1">
                                        <label for="name" class="block text-sm font-medium leading-6 text-gray-900">Description</label>
                                        <div class="mt-2">
                                            <textarea bind:value={description} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                          </div>
                                    </div>
                                    
                                    <div class="mt-3 flex items-center justify-end gap-x-6">
                                        <button on:click={submitPost} type="submit" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Submit</button>
                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>