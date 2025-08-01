export interface Article {
  id: number
  title: string;
  summary: string;
  content: string;
  category: string; 
  authorName: string;
  createdAt: string;
  commentsIds: number[];
  views: number;
}