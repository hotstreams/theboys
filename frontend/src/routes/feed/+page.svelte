<script lang="ts">
    import Header from '../../components/Header.svelte';

    import Newsfeed from '../../components/Newsfeed.svelte';

    let heroSelection: boolean = true
    let requestAnyHero: boolean = false
    let selectedHero: any = {}

    let showHeroScores: boolean = true
</script>

<Header />

<Newsfeed/>