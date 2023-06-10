import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';
import topLevelAwait from "vite-plugin-top-level-await";

export default defineConfig({
	build: {
		target: 'esnext'
	},
	plugins: [
		sveltekit(),
		topLevelAwait({
			// The export name of top-level await promise for each chunk module
			promiseExportName: "__tla",
			// The function to generate import names of top-level await promise in each chunk module
			promiseImportName: i => `__tla_${i}`
		  })
	],
	server: {
		fs: {
		  // Allow serving files from one level up to the project root
		  allow: ['..'],
		},
	  },
});
