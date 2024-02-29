<script>
    import { goto } from '$app/navigation';
    import { onMount } from 'svelte';
    import { getUser, isAuthenticated } from '../../components/Auth.js'

    onMount(() => {
        if (!isAuthenticated()) {
            goto('signin');
        } else if (getUser().role != 'SCIENTIST') {
            goto('/')
        }
    })
</script>

{#if isAuthenticated() && getUser().role == 'SCIENTIST'}
    <slot />
{/if}