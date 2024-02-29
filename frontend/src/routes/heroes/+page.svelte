<script lang="ts">
    import Header from '../../components/Header.svelte';

    import HeroRentRequest from '../../components/HeroRentRequest.svelte';
    import HeroScores from '../../components/HeroScores.svelte';
    import HeroSelection from '../../components/HeroSelection.svelte';
    import HeroView from '../../components/HeroView.svelte';

    let heroSelection: boolean = true
    let requestAnyHero: boolean = false
    let selectedHero: any = {}

    let showHeroScores: boolean = true
</script>

<Header />

{#if showHeroScores}
    <HeroScores bind:heroSelection bind:selectedHero on:view={() => showHeroScores = false}/>
{:else}
    <HeroView bind:heroSelection bind:selectedHero on:cancel={() => showHeroScores = true}/>
{/if}