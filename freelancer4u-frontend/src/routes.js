import Home from "./pages/Home.svelte";
import Freelancers from "./pages/Freelancers.svelte";
import Jobs from "./pages/Jobs.svelte";
import Account  from "./pages/Account.svelte";

export default {
    '/': Home,
    '/home': Home,
    '/freelancers': Freelancers,

    '/jobs': Jobs,
    '/account' : Account
}