<template>
  <div class="users datatable">
    <div class="caption">User</div>
    <table>
      <thead>
        <tr>
          <th>Id</th>
          <th>Login</th>
          <th>name</th>
        </tr>
      </thead>
      <tbody>
        <UserTableRow v-for="user in users" :user="user" :key="user.id"/>
      </tbody>
    </table>
  </div>
</template>

<script>
import UserTableRow from "@/components/middle/Users/UserTableRow";

export default {
  name: "Users",
  props: ["users"],
  components: {
    UserTableRow
  }
}
</script>

<style scoped>

</style>