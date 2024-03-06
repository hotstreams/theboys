<script lang="ts">
    import AddHeroCreation from "../../components/AddHeroCreation.svelte";
    import HeroCreations from "../../components/HeroCreations.svelte";

  let showAddVacancyComponent: boolean = false

</script>

{#if showAddVacancyComponent}
  <AddHeroCreation on:cancel={() => showAddVacancyComponent = false}/>
{:else}
  <HeroCreations on:add={() => showAddVacancyComponent = true}/>
{/if}