<template>
  <div id="app">
    <Header :userId="userId" :users="users"/>
    <Middle :posts="posts" :users="users" :comments="comments"/>
    <Footer :userCount="Object.keys(users).length"
            :postsCount="Object.keys(posts).length"/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return this.$root.$data;
  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      const users = Object.values(this.users).filter(u => u.login === login);
      if (users.length === 0) {
        this.$root.$emit("onEnterValidationError", "No such user");
      } else {
        this.userId = users[0].id;
        this.$root.$emit("onChangePage", "Index");
      }
    });

    this.$root.$on("onRegister", (login, name, password) => {
      if (login.length < 3 || login.length > 16) {
        this.$root.$emit("onRegisterValidationError", "Login should be between 3 and 16 characters long")
      } else if (name.length < 1 || name.length > 32) {
        this.$root.$emit("onRegisterValidationError", "Name should be between 1 and 32 characters long")
      } else if (!login.match(/^[a-z]+$/)) {
        this.$root.$emit("onRegisterValidationError", "Login should only contain lowercase latin letters");
      } else if (Object.values(this.users).filter(u => u.login === login).length !== 0) {
        this.$root.$emit("onRegisterValidationError", "Login is already in use");
      } else if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password cannot be empty");
      } else {
        const id = Math.max(...Object.keys(this.users)) + 1;
        const admin = false;
        this.$root.$set(this.users, id, {
          id, login, name, admin
        });
        this.userId = id;
        this.$root.$emit("onChangePage", "Index");
      }
    })

    this.$root.$on("onLogout", () => this.userId = null);

    this.$root.$on("onWritePost", (title, text) => {
      if (this.userId) {
        if (!title || title.length < 5) {
          this.$root.$emit("onWritePostValidationError", "Title is too short");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onWritePostValidationError", "Text is too short");
        } else {
          const id = Math.max(...Object.keys(this.posts)) + 1;
          this.$root.$set(this.posts, id, {
            id, title, text, userId: this.userId
          });
        }
      } else {
        this.$root.$emit("onWritePostValidationError", "No access");
      }
    });

    this.$root.$on("onEditPost", (id, text) => {
      if (this.userId) {
        if (!id) {
          this.$root.$emit("onEditPostValidationError", "ID is invalid");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onEditPostValidationError", "Text is too short");
        } else {
          let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
          if (posts.length) {
            posts.forEach((item) => {
              item.text = text;
            });
          } else {
            this.$root.$emit("onEditPostValidationError", "No such post");
          }
        }
      } else {
        this.$root.$emit("onEditPostValidationError", "No access");
      }
    });
  }
}
</script>

<style>
#app {

}
</style>
