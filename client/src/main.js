import Vue from "vue";
import App from "./App.vue";
import router from "./routes";

import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
Vue.use(BootstrapVue);
import vWow from "v-wow";

import VeeValidate, {
  ValidationObserver,
  ValidationProvider,
  Validator
} from "vee-validate";
Vue.use(VeeValidate, { fieldsBagName: "veeFields", errorBagName: "veeErrors" });
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);

import uk from "vee-validate/dist/locale/uk";
Validator.localize("uk", uk);

Vue.use(vWow);

Vue.config.productionTip = true;

new Vue({
  el: "#app",
  router,
  render: h => h(App)
}).$mount("#app");
