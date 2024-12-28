// Admin-specific API endpoints
// import { supabase } from './supabase'

export const adminAPI = {
  // User management
  async updateUserRole(userId, role) {
    // try {
    //   const { data, error } = await supabase
    //     .from('users')
    //     .update({ role })
    //     .eq('id', userId)
      
    //   if (error) throw error
    //   return data
    // } catch (error) {
    //   console.error('Error updating user role:', error)
    //   throw error
    // }
  },

  // Get all users with their roles
  async getUsers() {
    // try {
    //   const { data, error } = await supabase
    //     .from('users')
    //     .select('*')
      
    //   if (error) throw error
    //   return data
    // } catch (error) {
    //   console.error('Error fetching users:', error)
    //   throw error
    // }
  },

  // Get system reports
  async getReports() {
  //   try {
  //     const { data, error } = await supabase
  //       .from('reports')
  //       .select('*')
  //       .order('created_at', { ascending: false })
      
  //     if (error) throw error
  //     return data
  //   } catch (error) {
  //     console.error('Error fetching reports:', error)
  //     throw error
  //   }
  }
}