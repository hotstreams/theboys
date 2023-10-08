<script>
    import { goto } from '$app/navigation';
    import { onMount } from 'svelte';
    import { isAuthenticated } from '../../components/Auth.js'

    onMount(() => {
        if (!isAuthenticated()) {
            goto('signin');
        }
    })
</script>

{#if isAuthenticated()}
    <slot />
{/if}