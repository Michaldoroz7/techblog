import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { Article } from "../types/Article.type";

interface ArticleState {
  articles: Article[];
  article: Article | null;
  loading: boolean;
  error: string | null;
}

const initialState: ArticleState = {
  articles: [],
  article: null,
  loading: false,
  error: null,
};

export const fetchAllArticles = createAsyncThunk(
  "article/fetchAllArticles",
  async (_, thunkAPI) => {
    try {
      const response = await axios.get(
        "http://localhost:8080/article-service/api/articles"
      );
      return response.data as Article[];
    } catch (err: any) {
      return thunkAPI.rejectWithValue(
        err.response?.data?.message || "Nie udało się pobrać artykułów"
      );
    }
  }
);

export const fetchArticle = createAsyncThunk(
  "article/fetchArticle",
  async (_, thunkAPI) => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(
        "http://localhost:8080/article-service/api/articles/author",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data as Article[];
    } catch (err: any) {
      return thunkAPI.rejectWithValue(
        err.response?.data?.message ||
          "Nie udało się pobrać artykułów użytkownika"
      );
    }
  }
);

interface CreateArticlePayload {
  title: string;
  summary: string;
  content: string;
  category: string;
}

export const createArticle = createAsyncThunk(
  "article/createArticle",
  async (article: CreateArticlePayload, thunkAPI) => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/article-service/api/articles",
        article,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      return response.data as Article;
    } catch (err: any) {
      return thunkAPI.rejectWithValue(
        err.response?.data?.message || "Nie udało się dodać artykułu"
      );
    }
  }
);

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
      })
      .addCase(createArticle.pending, (state) => {
        state.loading = true;
        state.error = null;
        state.article = null;
      })
      .addCase(createArticle.fulfilled, (state, action) => {
        state.loading = false;
        state.article = action.payload;
        state.articles.unshift(action.payload);
      })
      .addCase(createArticle.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(fetchAllArticles.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAllArticles.fulfilled, (state, action) => {
        state.loading = false;
        state.articles = action.payload;
      })
      .addCase(fetchAllArticles.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export default articleSlice.reducer;
