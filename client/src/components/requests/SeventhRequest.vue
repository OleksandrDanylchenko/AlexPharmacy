<template>
  <div>
    <b-button v-b-toggle.request-7 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        7. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-7" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть CFN" class="mb-2">
              <b-form-select
                v-model="chosenFirstName"
                :options="clientsFirstNames"
                @change="handleSeventhRequest"
              />
            </b-input-group>
            <b-input-group prepend="Оберіть TM">
              <b-form-select
                v-model="chosenManufacturerTrademark"
                :options="manufacturersTrademarks"
                @change="handleSeventhRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            hover
            :items="clients"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
            class="m-0"
          >
            <template v-slot:table-busy>
              <div class="text-center text-success mt-1">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 1.5rem; height: 1.5rem;"
                />
              </div>
            </template>
            <template v-slot:cell(birthday)="data">
              {{ data.item.birthday | moment("DD.MM.YYYY") }}
            </template>
          </b-table>
        </b-card-text>
      </b-card>
    </b-collapse>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  name: "SeventhRequest",
  props: ["clientsFirstNames", "manufacturersTrademarks"],
  data() {
    return {
      requestText:
        "Знайти дні народження покупців з іменем CFN, які придбали принаймні усі продукти від виробника TM",
      fields: [
        {
          key: "birthday",
          label: "День народження",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenFirstName: null,
      chosenManufacturerTrademark: null,
      clients: []
    };
  },
  methods: {
    handleSeventhRequest() {
      if (this.chosenFirstName && this.chosenManufacturerTrademark) {
        this.isBusy = true;
        DataService.searchRequest(
          `clients/search/findClientByFirstNameBoughtAllManufacturerDrugs?firstName=${this.chosenFirstName}&trademark=${this.chosenManufacturerTrademark}`
        )
          .then(response => {
            this.clients = response.data._embedded.clients;
            this.isBusy = false;
          })
          .catch(error => {
            this.$emit("addError", `${error}`);
          });
      }
    }
  }
};
</script>
