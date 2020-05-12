<template>
  <div>
    <b-modal
      id="basketModal"
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
            id="basketForm"
          >
            <ValidationProvider rules="required" name="з датою покупки">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Дача покупки">
                  <b-form-datepicker
                    v-model="formDate"
                    placeholder=""
                    reset-button
                    locale="uk"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="з часом покупки">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Час покупки">
                  <b-form-timepicker
                    v-model="formTime"
                    placeholder=""
                    now-button
                    reset-button
                    locale="uk"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="клієнта">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Клієнт">
                  <b-form-select
                    v-model="formBasket.client.id"
                    :options="availableClients"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="препарату">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Препарат">
                  <b-form-select
                    v-model="formBasket.drug.id"
                    :options="availableDrugs"
                    :state="errors[0] ? false : valid ? true : null"
                  />
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
        <b-button type="submit" form="basketForm" variant="danger">
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
  name: "BasketModal",
  data() {
    return {
      formBasket: {
        id: null,
        buyingTime: null,
        client: {
          id: null
        },
        drug: {
          id: null
        }
      },
      formDate: null,
      formTime: null,
      basketsResource: "baskets",

      availableClients: [],
      clientsResource: "clients",
      availableDrugs: [],
      drugsResource: "drugs"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.basketsResource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of basket №" + this.processingId);
            let buyingTime = response.data.buyingTime;
            this.formDate = buyingTime.split("T").shift();
            this.formTime = buyingTime.split("T").pop();
            this.formBasket.client.id = response.data.client.id;
            this.formBasket.drug.id = response.data.drug.id;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.response.status !== 404) {
              this.$emit("addError", `${error}`);
              this.$bvModal.hide("basketModal");
            }
          });
        this.formBasket.id = this.processingId;
      });
    },
    loadChoices() {
      DataService.retrieveAllRecords(this.clientsResource)
        .then(response => {
          this.$log.debug(
            "Loaded available clients: " + response.data._embedded.clients
          );

          response.data._embedded.clients.forEach(client => {
            let clientOption = {
              value: client.id,
              text: client.firstName + " " + client.lastName
            };
            this.availableClients.push(clientOption);
          });

          if (this.availableClients.length === 0) {
            const clientsNotFoundError =
              "Таблиця клієнтів не містить записів. Не можна створити кошик";
            this.$log.debug(clientsNotFoundError);
            this.$emit("addError", clientsNotFoundError);
            this.$bvModal.hide("basketModal");
          }
        })
        .catch(error => {
          this.$log.debug(error);
          this.$emit("addError", error);
          this.$bvModal.hide("basketModal");
        });
      DataService.retrieveAllRecords(this.drugsResource)
        .then(response => {
          this.$log.debug("Loaded available drugs: " + response.data);

          response.data.forEach(drug => {
            let cityOption = {
              value: drug.id,
              text: drug.name
            };
            this.availableDrugs.push(cityOption);
          });

          if (this.availableDrugs.length === 0) {
            const drugsNotFoundError =
              "Таблиця препаратів не містить записів. Не можна створити кошик";
            this.$log.debug(drugsNotFoundError);
            this.$emit("addError", drugsNotFoundError);
            this.$bvModal.hide("basketModal");
          }
        })
        .catch(error => {
          this.$log.debug(error);
          this.$emit("addError", error);
          this.$bvModal.hide("basketModal");
        });
    },
    resetForm() {
      this.formBasket = {
        id: null,
        buyingTime: null,
        client: {
          id: null
        },
        drug: {
          id: null
        }
      };
      this.formDate = null;
      this.formTime = null;
    },
    handleSubmit() {
      this.formBasket.buyingTime = this.computedBuyingTime;
      if (this.formBasket.id < 0) this.$emit("addBasket", this.formBasket);
      else this.$emit("updateBasket", this.formBasket);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0 ? "Додайте кошик" : "Змініть кошик";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    },
    computedBuyingTime: function() {
      return this.formDate + "T" + this.formTime + "Z";
    }
  },
  created() {
    this.loadChoices();
  }
};
</script>
