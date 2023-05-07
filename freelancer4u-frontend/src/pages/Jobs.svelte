<script>
    import axios from "axios";
    import { user, jwt_token, myFreelancerId } from "../store";
    import { querystring } from "svelte-spa-router";

    const api_root = window.location.origin;

    let currentPage;
    let nrOfPages = 0;
    let defaultPageSize = 4;

    let earningsMin;
    let jobType;

    let jobs = [];
    let job = {
        description: null,
        detailDescription: null,
        earnings: null,
        jobType: null,
    };

    let loading = false;

    $: {
        let searchParams = new URLSearchParams($querystring);

        if (searchParams.has("page")) {
            currentPage = searchParams.get("page");
        } else {
            currentPage = "1";
        }
        getJobs();
    }

    function getJobs() {
        let query =
            "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;

        if (earningsMin) {
            query += "&min=" + earningsMin;
        }
        if (jobType && jobType !== "ALL") {
            query += "&type=" + jobType;
        }

        var config = {
            method: "get",
            url: api_root + "/api/job" + query,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                jobs = response.data.content;

                nrOfPages = response.data.totalPages;
            })
            .catch(function (error) {
                alert("Could not get jobs");
                console.log(error);
            });
    }

    function createJob() {
        loading = !loading

        var config = {
            method: "post",
            url: api_root + "/api/job",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + $jwt_token,
            },
            data: job,
        };

        axios(config)
            .then(function (response) {
                alert("Job created");
                getJobs();
            })
            .catch(function (error) {
                alert("Could not create Job");
                console.log(error);
            })
            .finally(function (){
                loading = !loading;
            });
    }

    function assignToMe(jobId) {
        var config = {
            method: "put",
            url: api_root + "/api/service/me/assignjob?jobId=" + jobId,
            headers: { Authorization: "Bearer " + $jwt_token },
        };
        axios(config)
            .then(function (response) {
                getJobs();
            })
            .catch(function (error) {
                alert("Could not assign job to me");
                console.log(error);
            });
    }

    function completeMyJob(jobId) {
        var config = {
            method: "put",
            url: api_root + "/api/service/me/completejob?jobId=" + jobId,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                console.log(JSON.stringify(response.data));
                getJobs();
            })
            .catch(function (error) {
                alert("Could not mark as completed");
                console.log(error);
            });
    }
</script>

{#if $user.user_roles && $user.user_roles.includes("admin")}
    <h1 class="mt-3">Create Job</h1>
    <form class="mb-5">
        <div class="row mb-3">
            <div class="col">
                <label class="form-label" for="description">Description</label>
                <input
                    bind:value={job.description}
                    class="form-control"
                    id="description"
                    type="text"
                />
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <label class="form-label" for="type">Type</label>
                <select
                    bind:value={job.jobType}
                    class="form-select"
                    id="type"
                    type="text"
                >
                    <option value="OTHER">OTHER</option>
                    <option value="TEST">TEST</option>
                    <option value="IMPLEMENT">IMPLEMENT</option>
                    <option value="REVIEW">REVIEW</option>
                </select>
            </div>
            <div class="col">
                <label class="form-label" for="earnings">Earnings</label>
                <input
                    bind:value={job.earnings}
                    class="form-control"
                    id="earnings"
                    type="number"
                />
            </div>
        </div>
        {#if loading}
            <button class="btn btn-primary" type="button" disabled>
                <span
                    class="spinner-border spinner-border-sm"
                    role="status"
                    aria-hidden="true"
                />
                <span class="sr-only">Loading...</span>
            </button>
        {:else}
            <button type="button" class="btn btn-primary" on:click={createJob}>Submit</button>
        {/if}
        
    </form>
{/if}

<h1>All Jobs</h1>
<div class="row my-3">
    <div class="col-auto">
        <label for="" class="col-form-label">Earnings: </label>
    </div>
    <div class="col-3">
        <input
            class="form-control"
            type="number"
            placeholder="min"
            id="earningsfilter"
            bind:value={earningsMin}
        />
    </div>
    <div class="col-auto">
        <label for="" class="col-form-label">Job Type: </label>
    </div>
    <div class="col-3">
        <select bind:value={jobType} class="form-select" id="typefilter" type="text">
            <option value="ALL" />
            <option value="OTHER">OTHER</option>
            <option value="TEST">TEST</option>
            <option value="IMPLEMENT">IMPLEMENT</option>
            <option value="REVIEW">REVIEW</option>
        </select>
    </div>

    <div class="col-3">
        <a
            class="btn btn-primary"
            href={"#/jobs?page=1&jobType=" +
                jobType +
                "&earningsMin=" +
                earningsMin}
            role="button">Apply</a
        >
    </div>
</div>

<table class="table">
    <thead>
        <tr>
            <th scope="col">Description</th>
            <th scope="col">Description Detail</th>
            <th scope="col">Type</th>
            <th scope="col">Earnings</th>
            <th scope="col">State</th>
            <th scope="col">FreelancerId</th>
            <th scope="col">Actions</th>
        </tr>
    </thead>
    <tbody>
        {#each jobs as job}
            <tr>
                <td>{job.description}</td>
                <td>{job.detailDescription}</td>
                <td>{job.jobType}</td>
                <td>{job.earnings}</td>
                <td>{job.jobState}</td>
                <td>{job.freelancerId}</td>
                <td>
                    {#if job.jobState === "ASSIGNED"}
                        <span class="badge bg-secondary">Assigned</span>
                    {:else if job.freelancerId === null}
                        <button
                            type="button"
                            class="btn btn-primary btn-sm"
                            on:click={() => {
                                assignToMe(job.id);
                            }}
                        >
                            Assign to me
                        </button>
                    {/if}
                    {#if job.jobState === "DONE"}
                        <span class="badge bg-secondary">Done</span>
                    {:else if job.jobState === "ASSIGNED" && job.freelancerId === $myFreelancerId }
                        <button
                            type="button"
                            class="btn btn-primary btn-sm"
                            on:click={() => {
                                completeMyJob(job.id);
                            }}
                        >
                            Complete Job
                        </button>
                    {/if}
                </td>
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
                    href={"#/jobs?page=" + (i + 1)}
                    >{i + 1}
                </a>
            </li>
        {/each}
    </ul>
</nav>