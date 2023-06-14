<script>
    import { goto } from '$app/navigation';
    import { onMount } from 'svelte';
    import { getUser, isAuthenticated } from '../../components/Auth.js'

    onMount(() => {
        if (!isAuthenticated()) {
            goto('signin');
        } else if (getUser().role != 'VISITOR') {
            goto('/')
        }
    })
</script>

{#if isAuthenticated() && getUser().role == 'VISITOR'}
    <slot />
{/if}