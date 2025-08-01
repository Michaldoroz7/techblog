import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";


interface StatisticsState {
    totalArticles: number;
    totalComments: number;
    totalUsers: number;
    lastArticle: {
        id: number;
        title: string;
        createdAt: string;
    };
    lastComment: {
        id: number;
        content: string;
        createdAt: string;
    };
    lastUser: {
        id: number | null;
        username: string;
        createdAt: string;
    };
    loading: boolean;
    error?: string;
    }

    const initialState: StatisticsState = {
        totalArticles: 0,
        totalComments: 0,
        totalUsers: 0,
        lastArticle: {
            id: 0,
            title: "",
            createdAt: "",
        },
        lastComment: {
            id: 0,
            content: "",
            createdAt: "",
        },
        lastUser: {
            id: null,
            username: "",
            createdAt: "",
        },
        loading: false,
        error: undefined,
    };

export const fetchStatistics = createAsyncThunk(
    "statistics/fetch",
    async (_, thunkAPI) => {
        try {
            const token = localStorage.getItem("token");
            const response = await axios.get(
                "http://localhost:8080/statistics-service/api/statistics",
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            return response.data as StatisticsState;
        } catch (err: any) {
            return thunkAPI.rejectWithValue(
                err.response?.data?.message || "Nie udało się pobrać statystyk"
            );
        }
    }
);

const statisticsSlice = createSlice({
    name: "statistics",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchStatistics.pending, (state) => {
                state.loading = true;
                state.error = undefined;
            })
            .addCase(fetchStatistics.fulfilled, (state, action) => {
                state.loading = false;
                state.totalArticles = action.payload.totalArticles;
                state.totalComments = action.payload.totalComments;
                state.totalUsers = action.payload.totalUsers;
                state.lastArticle = action.payload.lastArticle;
                state.lastComment = action.payload.lastComment;
                state.lastUser = action.payload.lastUser;
            })
            .addCase(fetchStatistics.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload as string;
            });
    }
});

export const { actions, reducer } = statisticsSlice;
export default statisticsSlice.reducer;

