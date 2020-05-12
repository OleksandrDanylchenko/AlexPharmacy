<template>
  <div>
    <b-modal
      id="diseaseModal"
      no-close-on-backdrop
      @show="loadValues"
      @hidden="resetForm"
    >
      <template v-slot:modal-title>
        <h3>{{ actionMessage }}</h3>
      </template>

      <template v-slot:default>
        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="diseaseForm"
          >
            <ValidationProvider
              rules="required|max:150"
              name="з назвою хвороби"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Назва хвороби">
                  <b-form-input
                    type="text"
                    v-model="formDisease.name"
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
        <b-button type="submit" form="diseaseForm" variant="danger">
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
      formDisease: {
        id: null,
        name: null
      },
      diseasesResource: "diseases"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.diseasesResource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of disease №" + this.processingId);
            this.formDisease.name = response.data.name;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.response.status !== 404) {
              this.$emit("addError", `${error}`);
              this.$bvModal.hide("diseaseModal");
            }
          });
        this.formDisease.id = this.processingId;
      });
    },
    resetForm() {
      this.formDisease = {
        id: null,
        name: null
      };
    },
    handleSubmit() {
      if (this.formDisease.id < 0) this.$emit("addDisease", this.formDisease);
      else this.$emit("updateDisease", this.formDisease);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте нову хворобу"
        : "Змініть хворобу";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    }
  }
};
</script>
