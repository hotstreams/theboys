<script lang="ts">
    import Header from '../../components/Header.svelte';
    import config from '../../../config.js';
    import { getAuthHeader, getUser } from '../../components/Auth.js'
    import { goto } from '$app/navigation';

    let firstName: string
    let lastName: string
    let dateOfBirth: string
    let sex: string
    let race: string
    let height: string
    let weight: string
    let address: string
    let description: string

    let validation: boolean = false
    let validationMsg: string

    function validate() {
      if (firstName == null &&
        lastName == null &&
        dateOfBirth == null &&
        sex == 'Does not matter' &&
        race == 'Does not matter' &&
        height == null &&
        weight == null &&
        address == null &&
        description == null
      ) {
        validation = true
        validationMsg = 'At least one field should be filled out'
        return false
      } else if (dateOfBirth != null && !/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/.test(dateOfBirth)) {
        validation = true
        validationMsg = 'Date of birth shoud be XXXX-XX-XX'
        return false
      } else if (parseInt(height) <= 0 || parseInt(weight) <= 0) {
        validation = true
        validationMsg = 'Height and weight shoud be positive'
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
        const res = await fetch(config.host + '/scientists/candidates', {
            method: 'POST',
            body: JSON.stringify({
                firstName,
                lastName,
                dateOfBirth,
                sex,
                race,
                height,
                weight,
                address,
                description,
            }),
            headers: {
              'Content-Type': "application/json",
              'Authorization': getAuthHeader() ?? ''
            }
        })

        if (res.ok) {
            goto('/scientist-candidate-requests')
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
                <h2 class="text-base font-semibold leading-7 text-gray-900">Request for finding a candidate</h2>
  
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
          <div class="sm:col-span-3">
            <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">First name</label>
            <div class="mt-2">
              <input bind:value={firstName} type="text" name="first-name" id="first-name" autocomplete="given-name" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
            </div>
          </div>
  
          <div class="sm:col-span-3">
            <label for="last-name" class="block text-sm font-medium leading-6 text-gray-900">Last name</label>
            <div class="mt-2">
              <input bind:value={lastName} type="text" name="last-name" id="last-name" autocomplete="family-name" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
            </div>
          </div>

            <div class="sm:col-span-3">
                <label for="date of birth" class="block text-sm font-medium leading-6 text-gray-900">Date of birth</label>
                <div class="mt-2">
                    <input bind:value={dateOfBirth} datepicker name="birth" type="date" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder="date of birth XXXX-XX-XX">
                </div>
            </div>

              <div class="sm:col-span-3">
                <label for="sex" class="block text-sm font-medium leading-6 text-gray-900">Sex</label>
                <div class="mt-2">
                    <select bind:value={sex} name="sex" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    <option value="Does not matter">Does not matter</option>
                    <option value="Female">Female</option>
                    <option value="Male">Male</option>
                    <option value="Transformer">Transformer</option>
                    </select>
                </div>
              </div>

              <div class="sm:col-span-3">
                <label for="race" class="block text-sm font-medium leading-6 text-gray-900">Race</label>
                <div class="mt-2">
                    <select bind:value={race} name="race" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    <option value="Does not matter">Does not matter</option>
                    <option value="White">White supreme</option>
                    <option value="Black">Black</option>
                    <option value="Asian">Asian</option>
                    </select>
                </div>
              </div>

              <div class="sm:col-span-3">
                <label for="height" class="block text-sm font-medium leading-6 text-gray-900">Height</label>
                <div class="mt-2">
                  <input bind:value={height} type="text" name="height" id="height" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                </div>
              </div>

              <div class="sm:col-span-3">
                <label for="weight" class="block text-sm font-medium leading-6 text-gray-900">Weight</label>
                <div class="mt-2">
                  <input bind:value={weight} type="text" name="weight" id="weight" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                </div>
              </div>

          <div class="col-span-full">
            <label for="street-address" class="block text-sm font-medium leading-6 text-gray-900">Address</label>
            <div class="mt-2">
              <input bind:value={address} type="text" name="street-address" id="street-address" autocomplete="street-address" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
            </div>
          </div>

          <div class="col-span-full">
            <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Description</label>
            <div class="mt-2">
              <textarea bind:value={description} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
            </div>
          </div>
        </div>
      </div>

    </div>
  
    <div class="mt-6 flex items-center justify-end gap-x-6">
      <button on:click={send} type="submit" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Send</button>
    </div>
  </div>
</div>
</div>
</div>
</div>