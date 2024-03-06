<script>
    import { goto } from '$app/navigation';
    import { onMount } from 'svelte';
    import { getUser, isAuthenticated } from '../../components/Auth.js'

    onMount(() => {
        if (!isAuthenticated()) {
            goto('signin');
        } else if (getUser().role != 'SCIENTIST' && getUser().role != 'MANAGER') {
            goto('/')
        }
    })
</script>

{#if isAuthenticated() && (getUser().role == 'SCIENTIST' || getUser().role == 'MANAGER')}
    <slot />
{/if}