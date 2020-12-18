<template>
  <div style="padding: 2rem 0">
    <div class="header">Add Comment</div>
    <div class="body">
      <form @submit.prevent="onAddComment">
        <div class="field">
          <div class="name">
            <label for="text">Text</label>
          </div>
          <div class="value">
            <textarea id="text" name="text" v-model="text"></textarea>
          </div>
        </div>
        <div class="field error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Add">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddComment",
  data: function () {
    return {
      text: "",
      error: ""
    }
  },
  props: {
    id: {
      type: Number
    }
  },
  methods: {
    onAddComment: function () {
      this.$root.$emit("onAddComment", this.text, this.id)
    }
  },
  beforeCreate() {
    this.$root.$on("onAddCommentValidationError", (error) => this.error = error)
    this.$root.$on("onAddCommentSuccess", () => this.text = "")
  }
}
</script>

<style scoped>

</style>