package news_reader.news.asap.appforyou.asapnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import utils.NewsArticle;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsArticleViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsArticleViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsArticleViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    String textToShow = "hello";
    NewsArticle newsArticle = null;

    public NewsArticleViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsArticleViewFragment newInstance(String param1, NewsArticle newsArticle) {
        NewsArticleViewFragment fragment = new NewsArticleViewFragment();
        Bundle args = new Bundle();
        args.putString("text", param1);
        args.putParcelable("newsArticle", newsArticle);
        fragment.setArguments(args);


        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( getArguments() != null ) {
            this.newsArticle = getArguments().getParcelable("newsArticle");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_article_view, container, false);

        TextView textView = (TextView) view.findViewById(R.id.fragment_newsArticle_title_textView);
        textView.setText(newsArticle.getNewsTitle());
        textView = (TextView) view.findViewById(R.id.fragment_newsArticle_description_textView);
        textView.setText(newsArticle.getNewsDescription());
        textView = (TextView) view.findViewById(R.id.fragment_newsArticle_publishedAt_textView);
        textView.setText(newsArticle.getNewsURL());
        if ( newsArticle.getNewsImage() != null ) {
            ImageView imageView = (ImageView) view.findViewById(R.id.fragment_newsArticle_imageView);
            imageView.setImageBitmap(newsArticle.getNewsImage());

        }

        return view;


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
