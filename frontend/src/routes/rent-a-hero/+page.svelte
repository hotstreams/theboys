  <script lang="ts">
    import Header from '../../components/Header.svelte';

    import HeroRentRequest from '../../components/HeroRentRequest.svelte';
    import HeroSelection from '../../components/HeroSelection.svelte';

    let heroSelection: boolean = true
    let requestAnyHero: boolean = false
    let selectedHero: any = {}
  </script>

  <Header />

<div class="root">
    {#if heroSelection}
      <HeroSelection bind:heroSelection bind:requestAnyHero bind:selectedHero />
    {:else}
      <HeroRentRequest bind:heroSelection bind:requestAnyHero bind:selectedHero/>
    {/if}
</div>