<script lang="ts">
    import Header from './Header.svelte';
    import config from '../../config.js';
    import { getAuthHeader, getUser } from './Auth.js'
    import { goto } from '$app/navigation';

    let firstName: string
    let lastName: string
    let phone: string
    let dateOfBirth: string
    let sex: string
    let race: string
    let height: string
    let weight: string
    let address: string
    let description: string
    let photo: any
    let medicalDoc: any

    let validation: boolean = false
    let validationMsg: string

    function validate() {
      if (dateOfBirth == null) {
          dateOfBirth = document.getElementsByName('birth')[0].getAttribute('value') ?? ""
      }
      console.log(dateOfBirth)
      if (firstName == null ||
        lastName == null ||
        phone == null ||
        dateOfBirth == null ||
        sex == null ||
        race == null ||
        height == null ||
        weight == null ||
        address == null ||
        description == null ||
        photo == null ||
        medicalDoc == null
      ) {
        validation = true
        validationMsg = 'All fields should be filled out'
        return false
      } else if (!/^(\+7|7|8)?[\s\-]?\(?[489][0-9]{2}\)?[\s\-]?[0-9]{3}[\s\-]?[0-9]{2}[\s\-]?[0-9]{2}$/.test(phone)) {
        validation = true
        validationMsg = 'Phone number shoud be X-XXX-XXX-XX-XX'
        return false
      } else if (!/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/.test(dateOfBirth)) {
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

      // const bytes = new Uint8Array(await file.arrayBuffer()));
      // await uploadBytes(uploadRef, bytes);

      // const reader = new FileReader();
      // reader.readAsArrayBuffer(new File(filename: photo));
      // reader.onload = function() {
      //     var arrayBuffer = (reader.result as ArrayBuffer) 
      //     var bytes = new Uint8Array(arrayBuffer);
      //     photo = bytes
      //     console.log('1')
      // }

      // reader.readAsArrayBuffer(medicalDoc);
      // reader.onload = function() {
      //     var arrayBuffer = (reader.result as ArrayBuffer) 
      //     var bytes = new Uint8Array(arrayBuffer);
      //     medicalDoc = bytes
      //     console.log('2')
      // }

      try {
        const res = await fetch(config.host + '/candidates', {
            method: 'POST',
            body: JSON.stringify({
                firstName,
                lastName,
                phone,
                dateOfBirth,
                sex,
                race,
                height,
                weight,
                address,
                description,
                photo: btoa(photo),
                medicalDoc: btoa(medicalDoc),
            }),
            headers: {
              'Content-Type': "application/json",
              'Authorization': getAuthHeader() ?? ''
            }
        })

        if (res.ok) {
            goto('/candidate-requests')
        } else {
            validation = true
            validationMsg = (await res.json()).error
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
          <div class="bg-white border border-gray-200 rounded-xl shadow-sm overflow-hidden dark:bg-slate-900 dark:border-gray-700">
            <div class="px-6 py-4 grid gap-3  md:items-center border-b border-gray-200 dark:border-gray-700">
              <div class="border-b border-gray-900/10 pb-12">
                <h2 class="text-base font-semibold leading-7 text-gray-900">Request to become a hero</h2>
  
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
            <label for="phone" class="block text-sm font-medium leading-6 text-gray-900">Phone</label>
            <div class="mt-2">
              <input bind:value={phone} id="phone" name="phone" type="phone" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" placeholder=" X-XXX-XXX-XX-XX">
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
            <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Describe yourself</label>
            <div class="mt-2">
              <textarea bind:value={description} id="about" name="about" rows="3" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
            </div>
          </div>
        </div>
      </div>

      <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
        <div class="sm:col-span-3">
            <label for="photo" class="block text-sm font-medium leading-3 text-gray-900">Photo</label>
            <div class="mt-2">
                <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                  <div class="text-center">
                    <svg class="mx-auto h-12 w-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                      <path fill-rule="evenodd" d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z" clip-rule="evenodd" />
                    </svg>
                    <div class="mt-4 flex text-sm leading-6 text-gray-600">
                      <label for="file-upload-photo" class="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
                        <span>Upload a file</span>
                        <input bind:value={photo} id="file-upload-photo" name="file-upload-photo" type="file" class="sr-only">
                      </label>
                    </div>
                    <p class="text-xs leading-5 text-gray-600">PNG, JPG up to 10MB</p>
                  </div>
                </div>      
            </div>
        </div>

        <div class="sm:col-span-3">
            <label for="med" class="block text-sm font-medium leading-3 text-gray-900">Medical document</label>
            <div class="mt-2">
                <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                  <div class="text-center">
                    <svg class="mx-auto h-12 w-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                      <path fill-rule="evenodd" d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z" clip-rule="evenodd" />
                    </svg>
                    <div class="mt-4 flex text-sm leading-6 text-gray-600">
                      <label for="file-upload-pdf" class="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
                        <span>Upload a file</span>
                        <input bind:value={medicalDoc} id="file-upload-pdf" name="file-upload-pdf" type="file" class="sr-only">
                      </label>
                    </div>
                    <p class="text-xs leading-5 text-gray-600">PDF up to 10MB</p>
                  </div>
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