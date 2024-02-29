<script lang="ts">
    import AddVacancy from "../../components/AddVacancy.svelte";
    import Vacancies from "../../components/Vacancies.svelte";

  let showAddVacancyComponent: boolean = false

</script>

{#if showAddVacancyComponent}
  <AddVacancy on:cancel={() => showAddVacancyComponent = false}/>
{:else}
  <Vacancies on:add={() => showAddVacancyComponent = true}/>
{/if}