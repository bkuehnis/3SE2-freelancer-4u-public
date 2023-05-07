<script>
    import axios from "axios";
    import { jwt_token } from "../store";
    import { querystring } from "svelte-spa-router";

    const api_root = window.location.origin;

    let currentPage;
    let nrOfPages = 0;
    let defaultPageSize = 4;

    let freelancers = [];
    let freelancer = {
        id: null,
        email: null,
        name: null,
    };

    $: {
        let searchParams = new URLSearchParams($querystring);

        if (searchParams.has("page")) {
            currentPage = searchParams.get("page");
        } else {
            currentPage = "1";
        }
        getFreelancers();
    }

    function getFreelancers() {
        let query =
            "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;

        var config = {
            method: "get",
            url: api_root + "/api/freelancer" + query,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                freelancers = response.data.content;
                nrOfPages = response.data.totalPages;
            })
            .catch(function (error) {
                alert("Could not get freelancers");
                console.log(error);
            });
    }

    function validateEmailAndcreateFreelancer() {
        var config = {
            method: "get",
            url: "https://disify.com/api/email/" + freelancer.email,
        };

        axios(config)
            .then(function (response) {
                console.log("Validated email " + freelancer.email);
                console.log(response.data);
                if (
                    response.data.format &&
                    !response.data.disposable &&
                    response.data.dns
                ) {
                    createFreelancer();
                } else {
                    alert("Email " + freelancer.email + " is not valid.");
                }
            })
            .catch(function (error) {
                alert("Could not validate email");
                console.log(error);
            });
    }

    function createFreelancer() {
        var config = {
            method: "post",
            url: api_root + "/api/freelancer",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + $jwt_token,
            },
            data: freelancer,
        };

        axios(config)
            .then(function (response) {
                alert("Freelancer created");
                getFreelancers();
            })
            .catch(function (error) {
                alert("Could not create Freelancer");
                console.log(error);
            });
    }
</script>

<h1 class="mt-3">Create Freelancer</h1>
<form class="mb-5">
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="name">Name</label>
            <input
                bind:value={freelancer.name}
                class="form-control"
                id="name"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="email">E-Mail</label>
            <input
                bind:value={freelancer.email}
                class="form-control"
                id="earnings"
                type="email"
            />
        </div>
    </div>
    <button type="button" class="btn btn-primary" on:click={validateEmailAndcreateFreelancer}
        >Submit</button
    >
</form>

<h1>All Freelancers</h1>
<table class="table">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">E-Mail</th>
        </tr>
    </thead>
    <tbody>
        {#each freelancers as freelancer}
            <tr>
                <td>{freelancer.name}</td>
                <td>{freelancer.email}</td>
            </tr>
        {/each}
    </tbody>
</table>
<nav>
    <ul class="pagination">
        {#each Array(nrOfPages) as _, i}
            <li class="page-item">
                <a
                    class="page-link"
                    class:active={currentPage == i + 1}
                    href={"#/freelancers?page=" + (i + 1)}
                    >{i + 1}
                </a>
            </li>
        {/each}
    </ul>
</nav>
