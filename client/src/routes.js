import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "Landing",
      component: () => import("./components/Landing")
    },
    {
      path: "/cities",
      name: "Cities",
      component: () => import("./components/cities/CitiesComponent")
    },
    {
      path: "/clients",
      name: "Clients",
      component: () => import("./components/clients/ClientsComponent")
    },
    {
      path: "/baskets",
      name: "Baskets",
      component: () => import("./components/baskets/BasketsComponent")
    },
    {
      path: "/diseases",
      name: "Diseases",
      component: () => import("./components/diseases/DiseasesComponent")
    },
    {
      path: "/drugs",
      name: "Drugs",
      component: () => import("./components/drugs/DrugsComponent")
    },
    {
      path: "/manufacturers",
      name: "Manufacturers",
      component: () =>
        import("./components/manufacturers/ManufacturersComponent")
    },
    {
      path: "/requests",
      name: "Requests",
      component: () => import("./components/requests/SimpleRequestsComponent")
    }
  ]
});

export default router;
