<script>
    import { goto } from '$app/navigation';
    import Header from '../components/Header.svelte';
</script>

<Header />

<body>
    <section class="h-[calc(100vh-61px)] bg-center bg-cover" style="background-image: url('https://img5.goodfon.com/original/2880x1800/8/58/filmy-serial-patsany-the-boys-aktiory-doroga-dym.jpg')">
        <div class="max-w-screen-xl px-40 pt-20 pb-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12 lg:pt-14">
            <div class="mr-auto place-self-center lg:col-span-2">
                <h1 class="max-w-2xl mb-4 text-4xl font-extrabold leading-none tracking-tight md:text-5xl xl:text-6xl text-white">Live your best life<br> be a hero </h1>
                <div class="space-y-4 sm:flex sm:space-y-0 sm:space-x-4">
                     <button on:click={() => goto('/become-a-hero')} class="inline-flex items-center justify-center w-full px-5 py-3 text-sm font-extrabold text-center text-white border-4 border-gray-200 rounded-lg sm:w-auto hover:border-purple-500 focus:ring-4 focus:ring-gray-100">
                        Become a hero
                     </button> 
               </div>
            </div>
        </div>
    </section>
</body>