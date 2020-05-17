<template>
  <div>
    <div class="mt-4">
      <Sidebar />
      <MessagesErrorsComponent
        :messages="messages"
        :errors="errors"
        @dismissMessages="dismissMessages"
        @dismissErrors="dismissErrors"
        class="fadeInRight"
        v-wow
      />

      <b-row>
        <b-col
          class="d-flex justify-content-between align-items-center mb-3 fadeInRight"
          v-wow
        >
          <h1 class="display-4 text-primary">Список кошиків:</h1>
          <b-button pill variant="outline-success" @click="openBasketModal(-1)">
            <i class="fas fa-plus-circle"></i>&nbsp;Додати новий кошик
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInRight" v-wow>
            <b-table
              id="basketsTable"
              hover
              :items="baskets"
              :fields="fields"
              :busy.sync="isBusy"
              primary-key="id"
            >
              <template v-slot:table-busy>
                <div class="text-center text-danger mt-2">
                  <b-spinner
                    type="grow"
                    class="align-middle"
                    style="width: 3rem; height: 3rem;"
                  />
                  <strong>&nbsp;Завантаження таблиці...</strong>
                </div>
              </template>

              <template v-slot:cell(clientId)="data">
                {{ data.item.client ? data.item.client.id : "∅" }}
              </template>
              <template v-slot:cell(clientName)="data">
                {{
                  data.item.client
                    ? data.item.client.firstName +
                      " " +
                      data.item.client.lastName
                    : "∅"
                }}
              </template>

              <template v-slot:cell(drugId)="data">
                {{ data.item.drug ? data.item.drug.id : "∅" }}
              </template>
              <template v-slot:cell(drugName)="data">
                {{
                  data.item.drug
                    ? data.item.drug.name +
                      " - " +
                      data.item.drug.manufacturer.trademark
                    : "∅"
                }}
              </template>

              <template v-slot:cell(buyingTime)="data">
                {{ data.item.buyingTime | moment("DD.MM.YYYY HH:MM") }}
              </template>

              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openBasketModal(data.item.id)"
                >
                  <i class="fa fa-edit"></i>
                </b-button>
              </template>
              <template v-slot:cell(deleteModal)="data">
                <b-button
                  pill
                  variant="outline-danger"
                  @click="openDeleteModal(data.item.id)"
                >
                  <i class="fa fa-trash"></i>
                </b-button>
              </template>
            </b-table>
          </div>
        </b-col>
      </b-row>
    </div>
    <BasketModal
      :processingId="processingId"
      @addBasket="addBasket"
      @updateBasket="updateBasket"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteBasket" />
  </div>
</template>

<script>
import Sidebar from "../common/Sidebar";
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import BasketModal from "./BasketModal";
import DeleteModal from "../common/DeleteModal";
import DataService from "../../service/DataService";

export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "BasketsComponent",
  components: {
    Sidebar,
    MessagesErrorsComponent,
    BasketModal,
    DeleteModal
  },
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-danger text-center",
          tdClass: "text-danger text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "clientId",
          label: "Код клієнта",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "clientName",
          label: "Клієнт",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "drugId",
          label: "Код препарату",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "drugName",
          label: "Препарат",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "buyingTime",
          label: "Дата/час покупки",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "editModal",
          label: "Змінити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "deleteModal",
          label: "Видалити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      baskets: [],
      basketsResource: "baskets",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshBaskets() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.basketsResource)
        .then(response => {
          this.$log.debug("Baskets loaded: ", response.data);
          this.baskets = response.data;
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error.response.data.errors);
        });
    },
    openBasketModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("basketModal");
    },
    addBasket(newBasket) {
      this.isBusy = true;
      DataService.addRecord(this.basketsResource, newBasket)
        .then(() => {
          this.$log.debug("Added basket " + newBasket);
          this.addMessage(`Новий кошик додано успішно`);
          this.refreshBaskets();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("basketModal");
    },
    updateBasket(updateBasket) {
      this.isBusy = true;
      DataService.updateRecord(this.basketsResource, updateBasket)
        .then(() => {
          this.$log.debug("Updater basket " + updateBasket);
          this.addMessage(`Кошик №${updateBasket.id} змінено успішно`);
          this.refreshBaskets();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("basketModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteBasket(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.basketsResource, id)
        .then(() => {
          this.$log.debug("Deleted basket №" + id);
          this.addMessage(`Видалення кошика №${id} виконано успішно`);
          this.refreshBaskets();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Видалення кошика №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshBaskets();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
