<template>
  <div>
    <div class="mt-4">
      <MessagesErrorsComponent
        :messages="messages"
        :errors="errors"
        @dismissMessages="dismissMessages"
        @dismissErrors="dismissErrors"
        class="fadeInLeft"
        v-wow
      />
      <b-row>
        <b-col
          class="d-flex justify-content-between align-items-center mb-3 fadeInLeft"
          v-wow
        >
          <h1 class="display-4 text-primary">Список клієнтів:</h1>
          <b-button pill variant="outline-success" @click="openClientModal(-1)">
            <i class="fas fa-plus-circle" />&nbsp;Додати нового клієнта
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInLeft" v-wow>
            <b-table
              id="clientsTable"
              hover
              :items="clients"
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
              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openClientModal(data.item.id)"
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
    <ClientModal
      :processingId="processingId"
      @addClient="addClient"
      @updateClient="updateClient"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteClient" />
  </div>
</template>

<script>
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import ClientModal from "./ClientModal";
import DeleteModal from "../common/DeleteModal";
import DataService from "../../service/DataService";

export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "ClientsComponent",
  components: {
    MessagesErrorsComponent,
    ClientModal,
    DeleteModal
  },
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-danger",
          tdClass: "text-danger"
        },
        {
          key: "firstName",
          label: "Ім'я",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center"
        },
        {
          key: "lastName",
          label: "Прізвище",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center"
        },
        {
          key: "birthday",
          label: "День народження",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center"
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
      clients: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      clientsResource: "clients",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshClients() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.clientsResource)
        .then(response => {
          this.$log.debug("Clients loaded: ", response.data);
          this.clients = response.data._embedded.clients;
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error.response.data.errors);
        });
    },
    openClientModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("clientModal");
    },
    addClient(newClient) {
      this.isBusy = true;
      DataService.addRecord(this.clientsResource, newClient)
        .then(() => {
          this.$log.debug("Added client " + newClient);
          this.addMessage(`Нового клієнта додано успішно`);
          this.refreshClients();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("clientModal");
    },
    updateClient(updateClient) {
      this.isBusy = true;
      DataService.updateRecord(
        this.clientsResource + "/" + this.processingId,
        updateClient
      )
        .then(() => {
          this.$log.debug("Updated client " + updateClient);
          this.addMessage(`Клієнта №${updateClient.id} змінено успішно`);
          this.refreshClients();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("clientModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteClient(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.clientsResource, id)
        .then(() => {
          this.$log.debug("Deleted client №" + id);
          this.addMessage(`Видалення клієнта №${id} виконано успішно`);
          this.refreshClients();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Видалення клієнта №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshClients();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
