import Vue from "vue";
import App from "./App.vue";
import router from "./routes";

import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import VeeValidate, {
  ValidationObserver,
  ValidationProvider,
  Validator
} from "vee-validate";
import uk from "vee-validate/dist/locale/uk";
import vWow from "v-wow";
import VueLogger from "vuejs-logger";

Vue.use(BootstrapVue);

Vue.use(VeeValidate, { fieldsBagName: "veeFields", errorBagName: "veeErrors" });
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);

Validator.localize("uk", uk);

Vue.use(vWow);

Vue.config.productionTip = true;

const options = {
  isEnabled: true,
  logLevel: "debug",
  stringifyArguments: false,
  showLogLevel: true,
  showMethodName: false,
  separator: "|",
  showConsoleColors: true
};

Vue.use(VueLogger, options);

new Vue({
  el: "#app",
  router,
  render: h => h(App)
}).$mount("#app");
