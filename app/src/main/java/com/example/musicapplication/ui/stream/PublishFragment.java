package com.example.musicapplication.ui.stream;

import android.content.Context;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.musicapplication.R;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.source.R5Camera;
import com.red5pro.streaming.source.R5Microphone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public R5Configuration configuration;
    private static final String TAG = "PublishFragment";

    protected Camera camera;
    protected boolean isPublishing = false;
    protected R5Stream stream;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PublishFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PublishFragment newInstance() {
        PublishFragment fragment = new PublishFragment();
        Bundle args = new Bundle();
//        args.putString( ARG_PARAM1, param1 );
//        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }

        configuration = new R5Configuration( R5StreamProtocol.RTSP, "192.168.0.104",  8554, "live", 1.0f);
        configuration.setLicenseKey("RMHJ-XVR6-A7XB-7916");
        configuration.setBundleID(getActivity().getPackageName());

        Log.d(TAG, "----------------- onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_publish, container, false );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button publishButton = getActivity().findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "-----------------click start button");
                onPublishToggle();
            }
        });
    }

    private void onPublishToggle() {
        Button publishButton = getActivity().findViewById(R.id.publishButton);
        if(isPublishing) {
            stop();
            Log.d(TAG, "-----------------stop()");
        }
        else {
            start();
            Log.d(TAG, "-----------------start()");
        }
        isPublishing = !isPublishing;
        publishButton.setText(isPublishing ? "stop" : "start");
    }

    public void start() {
        camera.stopPreview();

        stream = new R5Stream(new R5Connection(configuration));
        stream.setView((SurfaceView) getActivity().findViewById(R.id.surfaceView));

        R5Camera r5Camera = new R5Camera(camera, 320, 240);
        R5Microphone r5Microphone = new R5Microphone();

        stream.attachCamera(r5Camera);
        stream.attachMic(r5Microphone);

        stream.publish("red5prostream", R5Stream.RecordType.Live);
        camera.startPreview();
    }

    public void stop() {
        if(stream != null) {
            stream.stop();
            camera.startPreview();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isPublishing) {
            onPublishToggle();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        preview();
    }

    private void preview() {
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        SurfaceView surface = getActivity().findViewById(R.id.surfaceView);
        surface.getHolder().addCallback( (SurfaceHolder.Callback) this );
    }

    public class surfaceManage implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach( context );
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException( context.toString()
//                    + " must implement OnFragmentInteractionListener" );
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
