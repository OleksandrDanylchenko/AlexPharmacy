<template>
  <div>
    <b-modal
      id="clientModal"
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
            id="clientForm"
          >
            <ValidationProvider
              rules="required|alpha_spaces|max:150"
              name="імені"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Ім'я">
                  <b-form-input
                    type="text"
                    v-model="formClient.firstName"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider
              rules="required|alpha_spaces|max:150"
              name="прізвища"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Прізвище">
                  <b-form-input
                    type="text"
                    v-model="formClient.lastName"
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

<!--        TODO Between validation-->
        <ValidationProvider
          rules="required|date_format:yyyy-mm-dd"
          name="з датою дня народження"
        >
          <b-form-group slot-scope="{ valid, errors }">
            <b-input-group prepend="День народження">
              <b-form-input
                type="date"
                v-model="formClient.birthday"
                :min="minimumDate"
                :max="maximumDate"
                :state="errors[0] ? false : valid ? true : null"
              >
              </b-form-input>
              <b-form-invalid-feedback>
                {{ errors[0] }}
              </b-form-invalid-feedback>
            </b-input-group>
          </b-form-group>
        </ValidationProvider>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button variant="primary" @click="cancel()">
          Скасувати
        </b-button>
        <b-button type="submit" form="clientForm" variant="danger">
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
  name: "ClientModal",
  data() {
    return {
      formClient: {
        id: null,
        firstName: null,
        lastName: null,
        birthday: null
      },
      minimumDate: "1945-01-01",
      maximumDate: "2007-12-30",
      resource: "clients"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.resource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of client №" + this.processingId);
            this.formClient.firstName = response.data.firstName;
            this.formClient.lastName = response.data.lastName;
            this.formClient.birthday = response.data.birthday;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.status === 404) {
              this.$emit("addError", `${error}`);
            }
          });
        this.formClient.id = this.processingId;
      });
    },
    resetForm() {
      this.formClient = {
        id: null,
        firstName: null,
        lastName: null,
        birthday: null
      };
    },
    handleSubmit() {
      if (this.formClient.id < 0) this.$emit("addClient", this.formClient);
      else this.$emit("updateClient", this.formClient);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте нового клієнта"
        : "Змініть клієнта";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    }
  }
};
</script>
