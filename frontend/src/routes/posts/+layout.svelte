<script>
    import { goto } from '$app/navigation';
    import { getUser, isAuthenticated } from '../../components/Auth.js'
    import { onMount } from 'svelte';

    onMount(() => {
        if (!isAuthenticated()) {
            goto('/')
        }
    })

</script>

{#if isAuthenticated()}
    <slot />
{/if}