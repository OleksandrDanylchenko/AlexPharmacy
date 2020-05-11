<template>
  <div>
    <b-modal
      id="cityModal"
      no-close-on-backdrop
      @show="loadValues"
      @hidden="resetForm"
    >
      <template v-slot:modal-title>
        <h2>{{ actionMessage }}</h2>
      </template>

      <template v-slot:default>
        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="cityForm"
          >
            <ValidationProvider
              rules="required|alpha_spaces|max:150"
              name="з назвою міста"
            >
              <b-form-group slot-scope="{ valid, errors }" class="mb-0">
                <b-input-group prepend="Назва">
                  <b-form-input
                    type="text"
                    v-model="formCity.name"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>
          </b-form>
        </ValidationObserver>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button variant="primary" @click="cancel()">
          Скасувати
        </b-button>
        <b-button type="submit" form="cityForm" variant="danger">
          {{ action }}
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  props: ["processingId"],
  data() {
    return {
      formCity: {
        id: null,
        name: null
      },
      resource: "cities"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.resource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of city №" + this.processingId);
            this.formCity.name = response.data.name;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.status === 404) {
              this.$emit("addError", `${error}`);
            }
          });
        this.formCity.id = this.processingId;
      });
    },
    resetForm() {
      this.formCity = {
        id: null,
        name: null
      };
    },
    handleSubmit() {
      if (this.formCity.id < 0) this.$emit("addCity", this.formCity);
      else this.$emit("updateCity", this.formCity);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0 ? "Додайте нове місто" : "Змініть місто";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    }
  }
};
</script>
