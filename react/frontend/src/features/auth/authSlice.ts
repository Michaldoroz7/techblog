import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface AuthState {
  token: string | null;
  username: string | null;
  loading: boolean;
  error: string | null;
  justLoggedIn: boolean;
  registered: boolean;
}

const initialState: AuthState = {
  token: localStorage.getItem('token'),
  username: localStorage.getItem('username'),
  loading: false,
  error: null,
  justLoggedIn: false,
  registered:false,
};

export const login = createAsyncThunk(
  'auth/login',
  async (
    { username, password }: { username: string; password: string },
    thunkAPI
  ) => {
    try {
      const response = await axios.post(
        'http://localhost:8080/auth-service/api/auth/login',
        { username, password }
      );
      const token = response.data.token;
      const usernameHeader = response.headers['x-username'];

      localStorage.setItem('token', token);
      localStorage.setItem('username', usernameHeader)

      return { token, username: usernameHeader };
    } catch (err: any) {
      return thunkAPI.rejectWithValue(err.response?.data?.message || 'Logowanie niepomyślne');
    }
  }
);

export const register = createAsyncThunk(
  'auth/register',
  async (data: { username: string, email: string; password: string }, thunkAPI) => {
    try {
      const response = await axios.post('http://localhost:8080/auth-service/api/auth/register', data);
      return response.data;
    } catch (err: any) {
      return thunkAPI.rejectWithValue(err.response?.data?.message || 'Błąd rejestracji');
    }
  }
);


const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout(state) {
      state.token = null;
      state.username = null;
      localStorage.removeItem('token');
      localStorage.removeItem('username');
    },
  },
  extraReducers: (builder) => {
  builder
    .addCase(login.pending, (state) => {
      state.loading = true;
      state.error = null;
      state.justLoggedIn = false;
    })
    .addCase(login.fulfilled, (state, action) => {
      state.loading = false;
      state.token = action.payload.token;
      state.username = action.payload.username;
      state.justLoggedIn = true;
    })
    .addCase(login.rejected, (state, action) => {
      state.loading = false;
      state.error = action.payload as string;
      state.justLoggedIn = false;
    })
    .addCase(register.pending, (state) => {
      state.loading = true;
      state.error = null;
      state.registered = false;
    })
    .addCase(register.fulfilled, (state) => {
      state.loading = false;
      state.registered = true;
    })
    .addCase(register.rejected, (state, action) => {
      state.loading = false;
      state.error = action.payload as string;
    });
}
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
