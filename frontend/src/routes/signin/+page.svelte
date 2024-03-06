<script lang="ts">
    import { redirect } from '@sveltejs/kit';
    import { authenticate } from '../../components/Auth.js';
    import { goto } from '$app/navigation';
    import { browser } from '$app/environment';
    import Header from '../../components/Header.svelte';
    
    let username: string
    let password: string

    let error: boolean = false
    let errorMessage: string = "Unathenticated"

    function validate() {
        if (username == null || password == null || password.length < 8) {
            error = true
            errorMessage = 'Fields should be min 8 length'
            return false;
        } else {
            error = false
            return true;
        }
    }

    async function singin() {
        if (!validate()) {
            return;
        }

        const res = await authenticate(username, password)
        
        if (res.status) {
            goto('/')
        } else {
           error = true
           errorMessage = res.message
        }
    }

    function sleep(ms: any) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    async function ISU() {
        error = true
        errorMessage = 'Ha! Nice try, gotcha!'
        await sleep(2000);
        error = false
    }
</script>

<Header />

<form>
<div class="h-[calc(100vh-61px)] bg-gradient-to-tl from-green-400 to-indigo-900 w-full py-16 px-4">
    <div class="flex flex-col items-center justify-center">
        <div class="bg-white shadow rounded lg:w-1/3  md:w-1/2 w-full p-5 mt-16">
            <p class="focus:outline-none text-2xl font-extrabold leading-6 text-gray-800">Login to your account</p>

            <div class="mt-6 w-full">
                <label for="username" class="text-sm font-medium leading-none text-gray-800">
                    Username
                </label>
            <input bind:value={username} aria-labelledby="username" type="username" class="bg-gray-200 border rounded  text-xs font-medium leading-none text-gray-800 py-3 w-full pl-3 mt-2"/>
            </div>
                <div class="mt-6 w-full">
                    <label for="pass" class="text-sm font-medium leading-none text-gray-800">
                        Password
                    </label>
                   <div class="relative flex items-center justify-center">
                    <input bind:value={password} id="pass" type="password" class="bg-gray-200 border rounded  text-xs font-medium leading-none text-gray-800 py-3 w-full pl-3 mt-2"/>
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

                <div class="mt-8">
                    <button on:click={singin} class="focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 text-sm font-semibold leading-none text-white focus:outline-none bg-indigo-700 border rounded hover:bg-indigo-600 py-4 w-full">Sign in</button>
                </div>
                
            <p class="focus:outline-none text-sm mt-4 font-medium leading-none text-gray-500">Dont have account? 
                <a href="/signup" class="hover:text-gray-500 focus:text-gray-500 focus:outline-none focus:underline hover:underline text-sm font-medium leading-none  text-gray-800 cursor-pointer"> Sign up here</a>
            </p>
            <div class="w-full flex items-center justify-between py-5">
                <hr class="w-full bg-gray-400">
                <p class="text-base font-medium leading-4 px-2.5 text-gray-400">OR</p>
                <hr class="w-full bg-gray-400">
            </div>
            <button aria-label="Continue with github" class="focus:outline-none  focus:ring-2 focus:ring-offset-1 focus:ring-gray-700 py-3.5 px-4 border rounded-lg border-gray-700 flex items-center w-full mt-4">
               <img src="https://se.ifmo.ru/o/helios-theme/images/itmo-cs-logo.png" alt="github" width="100">                            
                <p on:click={ISU} class="text-base font-medium ml-4 text-gray-700">Continue with ISU</p>
            </button>
        </div>
    </div>
</div>
</form>