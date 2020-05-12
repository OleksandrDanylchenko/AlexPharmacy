<template>
  <div>
    <div class="mt-4">
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
          <h1 class="display-4 text-primary">Список виробників:</h1>
          <b-button
            pill
            variant="outline-success"
            @click="openManufacturerModal(-1)"
          >
            <i class="fas fa-plus-circle"></i>&nbsp;Додати нового виробника
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInRight" v-wow>
            <b-table
              id="manufacturersTable"
              hover
              :items="manufacturers"
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
              <template v-slot:cell(cityId)="data">
                {{ data.item.city.id }}
              </template>
              <template v-slot:cell(cityName)="data">
                {{ data.item.city.name }}
              </template>
              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openManufacturerModal(data.item.id)"
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
    <ManufacturerModal
      :processingId="processingId"
      @addManufacturer="addManufacturer"
      @updateManufacturer="updateManufacturer"
      @addError="addError"
    />
    <DeleteModal
      :processingId="processingId"
      @deleteRecord="deleteManufacturer"
    />
  </div>
</template>

<script>
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import ManufacturerModal from "./ManufacturerModal";
import DeleteModal from "../common/DeleteModal";
import DataService from "../../service/DataService";

export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "ManufacturersComponent",
  components: {
    MessagesErrorsComponent,
    ManufacturerModal,
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
          key: "trademark",
          label: "Торгова марка",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "cityId",
          label: "Код міста",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "cityName",
          label: "Назва міста",
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
      manufacturers: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      manufacturersResource: "manufacturers",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshManufacturers() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.manufacturersResource)
        .then(response => {
          this.$log.debug("Manufacturers loaded: ", response.data);
          this.manufacturers = response.data;
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error.response.data.errors);
        });
    },
    openManufacturerModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("manufacturerModal");
    },
    addManufacturer(newManufacturer) {
      this.isBusy = true;
      DataService.addRecord(this.manufacturersResource, newManufacturer)
        .then(() => {
          this.$log.debug("Added manufacturer " + newManufacturer);
          this.addMessage(`Нового виробника додано успішно`);
          this.refreshManufacturers();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("manufacturerModal");
    },
    updateManufacturer(updateManufacturer) {
      this.isBusy = true;
      DataService.updateRecord(this.manufacturersResource, updateManufacturer)
        .then(() => {
          this.$log.debug("Updater manufacturer " + updateManufacturer);
          this.addMessage(
            `Виробника №${updateManufacturer.id} змінено успішно`
          );
          this.refreshManufacturers();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("manufacturerModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteManufacturer(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.manufacturersResource, id)
        .then(() => {
          this.$log.debug("Deleted manufacturer №" + id);
          this.addMessage(`Видалення виробника №${id} виконано успішно`);
          this.refreshManufacturers();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Видалення виробника №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshManufacturers();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
