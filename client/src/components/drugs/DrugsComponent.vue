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
          <h1 class="display-4 text-primary">Список препаратів:</h1>
          <b-button pill variant="outline-success" @click="openDrugModal(-1)">
            <i class="fas fa-plus-circle"></i>&nbsp;Додати новий препарат
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInRight" v-wow>
            <b-table
              id="drugsTable"
              hover
              :items="drugs"
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

              <template v-slot:cell(instruction)="data">
                <b-button
                  size="sm"
                  variant="outline-info"
                  @click="openInstructionModal(data.item.id)"
                >
                  Детальніше...
                </b-button>
              </template>

              <template v-slot:cell(treatedDiseases)="data">
                <b-button
                  size="sm"
                  variant="outline-info"
                  v-b-toggle="'diseases' + data.item.id"
                  >Відкрити список</b-button
                >
                <b-collapse :id="'diseases' + data.item.id" class="mt-2">
                  <b-list-group>
                    <b-list-group-item
                      v-for="disease in data.item.diseases"
                      :key="disease.id"
                      class="my-1 py-1 px-0"
                    >
                      <div class="d-table m-auto">Код: {{ disease.id }}</div>
                      <div class="d-table m-auto">
                        Назва: {{ disease.name }}
                      </div>
                    </b-list-group-item>
                  </b-list-group>
                </b-collapse>
              </template>

              <template v-slot:cell(manufacturerId)="data">
                {{ data.item.manufacturer.id }}
              </template>
              <template v-slot:cell(manufacturerTrademark)="data">
                {{ data.item.manufacturer.trademark }}
              </template>

              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openDrugModal(data.item.id)"
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
    <InstructionModal :processingId="processingId" @addError="addError" />
    <DrugModal
      :processingId="processingId"
      @addDrug="addDrug"
      @updateDrug="updateDrug"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteDrug" />
  </div>
</template>

<script>
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import DrugModal from "./DrugModal";
import InstructionModal from "./InstructionModal";
import DeleteModal from "../common/DeleteModal";
import DataService from "../../service/DataService";

export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "DrugsComponent",
  components: {
    MessagesErrorsComponent,
    InstructionModal,
    DrugModal,
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
          key: "name",
          label: "Назва",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "instruction",
          label: "Інструкція",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "treatedDiseases",
          label: "Цільові хвороби",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "manufacturerId",
          label: "Код виробника",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "manufacturerTrademark",
          label: "Назва виробника",
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
      drugs: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      drugsResource: "drugs",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshDrug() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.drugsResource)
        .then(response => {
          this.$log.debug("Drugs loaded: ", response.data);
          this.drugs = response.data;
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error.response.data.errors);
        });
    },
    openInstructionModal(id) {
      this.processingId = id;
      this.$bvModal.show("instructionModal");
    },
    openDrugModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("drugModal");
    },
    addDrug(newDrug) {
      this.isBusy = true;
      DataService.addRecord(this.drugsResource, newDrug)
        .then(() => {
          this.$log.debug("Added drug " + newDrug);
          this.addMessage(`Новий препарат додано успішно`);
          this.refreshDrug();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("drugModal");
    },
    updateDrug(updateDrug) {
      this.isBusy = true;
      DataService.updateRecord(this.drugsResource, updateDrug)
        .then(() => {
          this.$log.debug("Update drug " + updateDrug);
          this.addMessage(`Препарат №${updateDrug.id} змінено успішно`);
          this.refreshDrug();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(error.response.data.errors);
        });
      this.isBusy = false;
      this.$bvModal.hide("drugModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteDrug(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.drugsResource, id)
        .then(() => {
          this.$log.debug("Deleted drug №" + id);
          this.addMessage(`Видалення препарату №${id} виконано успішно`);
          this.refreshDrug();
        })
        .catch(error => {
          this.$log.debug(error.response.data.errors);
          this.addError(`Видалення препарату №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshDrug();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
