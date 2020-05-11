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
      component: () => import("./components/CitiesComponent")
    },
    {
      path: "/clients",
      name: "Clients",
      component: () => import("./components/ClientsComponent")
    },
    {
      path: "/baskets",
      name: "Baskets",
      component: () => import("./components/BasketsComponent")
    },
    {
      path: "/diseases",
      name: "Diseases",
      component: () => import("./components/DiseasesComponent")
    },
    {
      path: "/drugs",
      name: "Drugs",
      component: () => import("./components/DrugsComponent")
    },
    {
      path: "/manufacturers",
      name: "Manufacturers",
      component: () => import("./components/ManufacturersComponent")
    }
  ]
});

export default router;
