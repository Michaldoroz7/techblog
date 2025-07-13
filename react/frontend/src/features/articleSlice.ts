import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { Article } from "../types/Article.type";

interface ArticleState{
    articles: Article[] | null;
    loading: boolean,
    error: string | null,
}

const initialState: ArticleState = {
    articles: [],
    loading: false,
    error: null,
}

export const fetchArticle = createAsyncThunk('article/fetchArticle',  async (_, thunkAPI) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('http://localhost:8080/article-service/api/articles/author', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data as Article[];
    } catch (err: any) {
      return thunkAPI.rejectWithValue(err.response?.data?.message || 'Nie udało się pobrać artykułów użytkownika');
    }
  });


const articleSlice = createSlice({
    name: "article",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
          .addCase(fetchArticle.pending, (state) => {
            state.loading = true;
            state.error = null;
          })
          .addCase(fetchArticle.fulfilled, (state, action) => {
            state.loading = false;
            state.articles = action.payload;
          })
          .addCase(fetchArticle.rejected, (state, action) => {
            state.loading = false;
            state.error = action.payload as string;
          });
      },}
)

export default articleSlice.reducer;