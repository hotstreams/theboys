<script>
    import { goto } from '$app/navigation';
    import { getUser, isAuthenticated } from '../../components/Auth.js'
    import { onMount } from 'svelte';

    onMount(() => {
        if (!isAuthenticated()) {
            goto('signin');
        } else if (getUser().role != 'HERO') {
            goto('/')
        }
    })

</script>

{#if isAuthenticated() && getUser().role == 'HERO'}
    <slot />
{/if}