import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface Quote {
  en: string;
  author: string;
  content: string;
}

interface QuoteState {
  quote: Quote | null;
  loading: boolean;
  error: string | null;
}

const initialState: QuoteState = {
  quote: null,
  loading: false,
  error: null,
};

export const fetchQuote = createAsyncThunk('quote/fetchQuote', async (_, thunkAPI) => {
  try {
    const response = await axios.get('https://api.quotable.io/quotes/random?tags=technology');
    const data = await response.data;
    return data;
  } catch (err) {
    return thunkAPI.rejectWithValue('Nie udało się pobrać cytatu.');
  }
});

const quoteSlice = createSlice({
  name: 'quote',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchQuote.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchQuote.fulfilled, (state, action) => {
        state.loading = false;
        state.quote = action.payload;
      })
      .addCase(fetchQuote.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export default quoteSlice.reducer;
