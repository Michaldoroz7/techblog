export interface User {
  username: string;
  email: string;
  createdAt: string;
  role: 'USER' | 'ADMIN'; 
}
